from django.shortcuts import render

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
    pass

def report_submit(request):
    pass

def report_accept(request, reportid):
    pass

def report_reject(request, reportid):
    pass

def report(request):
    pass

def job(request, jobid):
    pass

def job_accept(request, jobid):
    pass

def job_reject(request, jobid):
    pass

