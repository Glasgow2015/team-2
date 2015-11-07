from django.shortcuts import render
from django.contrib.auth import authenticate, login
from django.contrib.auth.decorators import login_required
from django.views.decorators.csrf import csrf_exempt
from django.http import HttpResponse

import json

from adminapi.models import Job, Area


def index(request):
    return render(request, 'index.html')

def volunteers(request):
    context_dict = {}
    context_dict['volunteers'] = volunteers
    volunteers = UserVolunteer.objects.all()

    return render(request, '', context_dict)

def volunteer_apply(request):
    # POST
    json_req = request.body
    jdict = json.loads(json_req)[0]

    u = User.objects.get_or_create(username=jdict['username'],
                                   first_name=jdict['first_name'],
                                   last_name=jdict['last_name'],
                                   password=jdict['password'])[0]
    UserVolunteer.objects.create(user = u,
                                 phone_number=jdict['phone_number'])

    return HttpResponse(status=200)
    

def volunteer_accept(request, userid):
    context_dict = {}
    try:
        u = User.objects.get(id=userid)
        v = UserVolunteer.get(user=u)
        v.accepted=True
    except Entry.DoesNotExist:
        return HttpResponse(404)

    return volunteer(request, userid)

def volunteer_reject(request, userid):
    u = User.objects.get(id=userid)
    v = UserVolunteer.get(user=u)
    v.delete()
    u.delete()

    return volunteers(request)

def volunteer_jobs(request, userid):
    context_dict = {}

    try:
        user_v = User.objects.get(id=userid)
        vol = UserVolunteer.objects.get(user=user_v)
        context_dict['jobs'] = JobsList.objects.get(volunteer=vol)
    except Entry.DoesNotExist:
        return HttpResponse(404)

    return render(request, '', context_dict)

def volunteer_assign(request, userid, jobid):
    pass

def volunteer(request, userid):
    context_dict = {}

    try:
        user_v = User.objects.get(id=userid)
        volunteer = UserVolunteer.objects.get(user=user_v)
        context_dict['phone_number'] = volunteer.phone_number
        context_dict['username'] = user_v.username
        context_dict['first_name'] = user_v.first_name
        context_dict['last_name'] = user_v.last_name

    except Entry.DoesNotExist:
        return HttpResponse(404)

    return render(request, '', context_dict)

        
def reports(request):
    # GET 
    context_dict = {}

    # reports
    reports = Job.objects.all()
    context_dict['reports'] = reports
    
    return render(request, 'reports.html', context_dict)

@csrf_exempt
def report_submit(request):
    # POST
    json_req = str(request.body)[2:-1].replace("\\n", "")
    print(json_req)
    jdict = json.loads(json_req)

    # JOB object -> db
    try:
        Job.objects.create(name=jdict['name'],
                       #created=jdict['created'],
                       #completed=jdict['completed'],
                       #accepted=jdict['accepted'],
                       latitude=jdict['latitude'],
                       longitude=jdict['longitude'],
                       location=Area.objects.get(name=jdict['location']),
                       description=jdict['description'])
    except:
        return HttpResponse(status=503)
        
    # OK
    return HttpResponse(status=200)

def report_accept(request, reportid):
    # mark report as accepted
    rep = Job.objects.get(id=reportid)
    rep.accepted = True

    # refresh page
    return report(request, reportid)

def report_reject(request, reportid):
    # delete report alltogether
    rep = Job.objects.get(id=reportid)
    rep.delete()

    # refresh page
    return report(request, reportid)

def report(request, report_id):
    # GET
    context_dict = {}

    # fetch report data
    report = Job.objects.get(id=report_id)
    context_dict['report_name'] = report.name
    context_dict['report_creation_date'] = report.created
    context_dict['report_completion_date'] = report.completed
    context_dict['report_accepted'] = report.accepted
    context_dict['report_latitude'] = report.latitude
    context_dict['report_longitude'] = report.longitude
    context_dict['report_description'] = report.description

    return render(request, '', context_dict)

def job(request, jobid):
    # GET
    context_dict = {}
    
    # fetch report data
    report = Job.objects.get(id=report_id)
    context_dict['report_name'] = report.name
    context_dict['report_creation_date'] = report.created
    context_dict['report_completion_date'] = report.completed
    context_dict['report_accepted'] = report.accepted
    context_dict['report_latitude'] = report.latitude
    context_dict['report_longitude'] = report.longitude
    context_dict['report_description'] = report.description
    
    return JsonResponse(context_dict)


# def user_login(reuest):
#
#     if request.method == 'POST':
#         username = request.POST.get('username')
#         password = request.POST.get('password')
#
#         user = authenticate(username=userame, password=password)
#
#         if user:
#             if user.is.active:
#                 login(request, login)
#
    
def job_accept(request, jobid):
    # mark job as accepted
    try:
        job = Job.objects.get(id=jobid)
        job.accepted=True
    except Entry.DoesNotExist:
        return HttpResponse(status=404)

    return HttpResponse(status=200)

def job_reject(request, jobid):
    pass

