from django.shortcuts import render
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required
from django.http import HttpResponse, HttpResponseRedirect
from django.core.exceptions import ObjectDoesNotExist
from django.views.decorators.csrf import csrf_exempt
from django.http import HttpResponse
from django.contrib.auth.models import User
from django.core import serializers
from adminapi.models import Job, Area, UserVolunteer, JobsList, AreasList

import json
import logging

logging.basicConfig(filename='wtf.log',level=logging.INFO)
logger = logging.getLogger(__name__)


def volunteers(request):
    """ Display list of all volunteering candidates """

    context_dict = {}
    volunteers = UserVolunteer.objects.filter(accepted=False)
    context_dict['volunteers'] = volunteers

    return render(request, 'volunteers.html', context_dict)


def current_volunteers(request):
    """ Display list of all accepted volunteers """

    context_dict = {}
    volunteers = UserVolunteer.objects.filter(accepted=True)
    context_dict['volunteers'] = volunteers

    return render(request, 'current_volunteers.html', context_dict)


@csrf_exempt
def volunteer_apply(request):
    """ Records a new volunteer """
    # parse app user's JSON POST request
    json_req = str(request.body)[2:-1].replace("\\n", "")
    jdict = json.loads(json_req)

    # enter new user into DB
    u = User.objects.get_or_create(username=jdict['username'],
                                   first_name=jdict['first_name'],
                                   last_name=jdict['last_name'],
                                   password=jdict['password'])[0]
    UserVolunteer.objects.create(user = u,
                                 phone_number=jdict['phone_number'],
                                 address="",
                                 skills="")

    # JSON "success" response back to app 
    return HttpResponse(json.dumps({"success": "true"}))


@login_required
@csrf_exempt
def volunteer_accept(request, userid):
    """ Admin accepts a volunteer application """

    context_dict = {}
    try:
        # update user's record in the DB
        u = User.objects.get(id=userid)
        v = UserVolunteer.objects.get(user=u)
        v.accepted=True
        v.save()
        
    except User.DoesNotExist:
        return HttpResponse(status=404)

    return HttpResponse(status=200)


@login_required
@csrf_exempt
def volunteer_reject(request, userid):
    """ Admin rejects a volunteer application """

    # "erase" both as a user and a volunteer
    u = User.objects.get(id=userid)
    v = UserVolunteer.objects.get(user=u)
    v.delete()
    u.delete()

    return volunteers(request)


def volunteer_jobs(request, userid):
    """ Inspect a user's jobs list """

    try:
        user_v = User.objects.get(id=userid)
        vol = UserVolunteer.objects.get(user=user_v)
        jobs_list = JobsList.objects.filter(volunteer=vol)
        jobs = [j.job for j in jobs_list]
        jobs_dict = {}
        for job in jobs:
            jobs_dict[job.name] = {
                "name": job.name,
                "created": str(job.created),
                "completed": job.completed,
                "accepted": job.accepted,
                "latitude": job.latitude,
                "longitude": job.longitude,
                "description": job.description,
                "location": job.location.name,
            }
        
    except User.DoesNotExist:
        return HttpResponse(404)

    # send back all of the user's jobs as JSON
    return HttpResponse(json.dumps(jobs_dict))


@login_required
@csrf_exempt
def volunteer_assign(request, userid, jobid):
    """ Assign a job to a volunteer """

    try:
        user_v = User.objects.get(id=userid)
        vol = UserVolunteer.objects.get(user=user_v)
        job_v = Job.objects.get(id=jobid)
        JobsList.objects.get_or_create(volunteer=vol,
                                       job=job_v)
    except User.DoesNotExist:
        return HttpResponse(status=404)

    return HttpResponse(status=200)

        
def reports(request):
    """ View a list of all reports """
    
    context_dict = {}
    # fetch reports
    reports = Job.objects.all()
    context_dict['reports'] = reports
    
    return render(request, 'reports.html', context_dict)


@csrf_exempt
def report_submit(request, userid):
    """ User of the app submits a report """

    # parse app's JSON request
    logger.info(request.body)
    json_req = str(request.body)[2:-1].replace("\\n", "")
    print(json_req)
    jdict = json.loads(json_req)

    user = User.objects.get(id=userid)
    # enter new report into DB
    Job.objects.create(name=jdict['name'],
                       latitude=jdict['latitude'],
                       longitude=jdict['longitude'],
                       location=Area.objects.get(name=jdict['location']),
                       description=jdict['description'],
                       creator=user)
        
    # success ack back to app as JSON
    return HttpResponse(json.dumps({'success': 'true'}))


@login_required
@csrf_exempt
def report_accept(request, reportid):
    """ Admin accepts a report """

    # mark report as accepted
    rep = Job.objects.get(id=reportid)
    rep.accepted = True
    rep.save()

    # OK
    return HttpResponse(status=200)


@login_required
@csrf_exempt
def report_reject(request, reportid):
    """ Admin rejects a report """

    # delete report alltogether
    rep = Job.objects.get(id=reportid)
    rep.delete()

    # OK
    return HttpResponse(status=200)


def report(request, reportid):
    """ Admin views a report """

    context_dict = {}

    # fetch report data
    this_job = Job.objects.get(id=reportid)
    this_volunteers = UserVolunteer.objects.all()
    this_job_list = JobsList.objects.all()

    for this_volunteer in this_volunteers:
        for job_list_item in this_job_list:
            if job_list_item.volunteer.id == this_volunteer.id:
                this_volunteer.assigned = True

    context_dict['report'] = this_job
    context_dict['volunteers'] = this_volunteers
    context_dict['arealist'] = AreasList.objects.all()

    return render(request, 'report.html', context_dict)


@csrf_exempt
def job(request, jobid):
    """ User views a job from the app """

    context_dict = {}
    
    # fetch report data
    report = Job.objects.get(id=jobid)
    context_dict['report_name'] = report.name
    context_dict['report_creation_date'] = report.created
    context_dict['report_completion_date'] = report.completed
    context_dict['report_accepted'] = report.accepted
    context_dict['report_latitude'] = report.latitude
    context_dict['report_longitude'] = report.longitude
    context_dict['report_description'] = report.description

    # job info back to app as JSON
    return JsonResponse(context_dict)


@login_required
def user_logout(request):
    logout(request)
    # user is redirected back to the index page
    return HttpResponseRedirect('/')

    
def user_login(request):

    if request.method == 'POST':
        username = request.POST.get('username')
        password = request.POST.get('password')

        user = authenticate(username=username, password=password)

        if user:
            if user.is_active:
                # valid active account
                login(request, user)
                return HttpResponseRedirect('/reports')

            else:
                # inactive account
                return HttpResponse("Account disabled")

        else:
            # bad login
            return HttpResponse("Invalid login details")

    else:
        return render(request, 'login.html', {})


@login_required
def job_accept(request, jobid):
    """ Admin marks a job as accepted """

    try:
        job = Job.objects.get(id=jobid)
        job.accepted=True
        job.save()

    except Job.DoesNotExist:
        return HttpResponse(status=404)

    # OK
    return HttpResponse(status=200)


@login_required
def job_reject(request, jobid):
    """ Admin rejects a job """

    try:
        job = Job.objects.get(id=jobid)
        job.delete()

    except Job.DoesNotExist:
        return HttpResponse(status=404)

    # OK
    return HttpResponse(status=200)


@login_required
def assign_volunteer(request):

    # assigns or un assigns a volunteer ot a job
    # called by jquery

    # response for ajax
    ajax_response = "Failure"

    if request.method == 'GET':

        volunteer_id = request.GET['volunteer_id']
        job_id = request.GET['job_id']

        this_volunteer = UserVolunteer.objects.get(id=volunteer_id)
        this_job = Job.objects.get(id=job_id)

        # Assign a volunteer to a new job
        if not JobsList.objects.filter(volunteer=this_volunteer, job=this_job).exists():
            this_job_list_item = JobsList.objects.create(volunteer=this_volunteer, job=this_job)
            this_job_list_item.save()
            ajax_response = "Success"

        # Un assign a volunteer from a job
        else:
            JobsList.objects.filter(volunteer=this_volunteer, job=this_job).delete()
            ajax_response = "Success"

    return HttpResponse(ajax_response)


