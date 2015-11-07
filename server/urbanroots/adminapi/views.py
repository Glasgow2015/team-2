from django.shortcuts import render
from models import Job

def index(request):
    return render(request, 'index.html')

def volunteers(request):
    pass

def volunteer_apply(request):
    pass

def volunteer_accept(request, userid):
    pass

def volunteer_reject(request, userid):
    pass

def volunteer_jobs(request, userid):
    pass

def volunteer_assign(request, userid, jobid):
    pass

def volunteer(request):
    pass

def reports(request):
    # GET 
    context_dict = {}

    # reports
    reports = Job.objects.all()
    context_dict['reports'] = reports
    
    return render(request, 'reports.html', context_dict)

def report_submit(request):
    # POST
    json_req = request.body
    jdict = json.loads(json_req)[0]

    # JOB object -> db
    try:
        Job.objects.create(name=jdict['name'],
                       created=jdict['created'],
                       completed=jdict['completed'],
                       accepted=jdict['accepted'],
                       latitude=jdict['latitude'],
                       longitude=jdict['longitude'],
                       description=jdict['description'])
    except:
        # Fail
        return HttpResponse(status=404)
        
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
    report = Jobs.objects.get(id=report_id)
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
    report = Jobs.objects.get(id=report_id)
    context_dict['report_name'] = report.name
    context_dict['report_creation_date'] = report.created
    context_dict['report_completion_date'] = report.completed
    context_dict['report_accepted'] = report.accepted
    context_dict['report_latitude'] = report.latitude
    context_dict['report_longitude'] = report.longitude
    context_dict['report_description'] = report.description
    
    return JsonResponse(context_dict)



    

def job_accept(request, jobid):
    pass

def job_reject(request, jobid):
    pass

