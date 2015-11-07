from django.conf.urls import patterns, url
from adminapi import views

urlpatterns = patterns('',
                       url(r'^$', views.index, name='index'),
)
