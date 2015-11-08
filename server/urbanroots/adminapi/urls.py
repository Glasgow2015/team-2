from django.conf.urls import patterns, url
from adminapi import views

urlpatterns = patterns('',
                       url(r'^$', views.user_login),

                       url(r'^volunteer/$', views.volunteers),
                       url(r'^current_volunteers/$', views.current_volunteers),
                       url(r'^volunteer/apply$', views.volunteer_apply),
                       url(r'^volunteer/(?P<userid>[\w\-]+)/accept/$', views.volunteer_accept),
                       url(r'^volunteer/(?P<userid>[\w\-]+)/reject/$', views.volunteer_reject),
                       url(r'^volunteer/(?P<userid>[\w\-]+)/assign/(?P<jobid>[\w\-]+)$', views.volunteer_assign),
                       url(r'^volunteer/(?P<userid>[\w\-]+)/jobs$', views.volunteer_jobs),
                       #url(r'^volunteer/(?P<userid>[\w\-]+)$', views.volunteer),

                       url(r'^reports/$', views.reports),
                       url(r'^report/submit$', views.report_submit),
                       url(r'^report/submit/(?P<userid>[\w\-]+)$', views.report_submit),
                       url(r'^report/(?P<reportid>[\w\-]+)/accept/$', views.report_accept),
                       url(r'^report/(?P<reportid>[\w\-]+)/reject/$', views.report_reject),
                       url(r'^report/(?P<reportid>[\w\-]+)$', views.report),

                       url(r'^job/(?P<jobid>[\w\-]+)$', views.job),
                       url(r'^job/(?P<jobid>[\w\-]+)/accept/$', views.job_accept),
                       url(r'^job/(?P<jobid>[\w\-]+)/reject/$', views.job_reject),

                       url(r'^assign_volunteer/$', views.assign_volunteer), # for jquery assign

                       url(r'^login/$', views.user_login, name='login'),
                       url(r'^logout/$', views.user_logout, name='logout'),
)
