from django.contrib import admin
from adminapi.models import Area, Category, Job, Skill, UserVolunteer, UserOwner, AreasList

admin.site.register(Area)
admin.site.register(Category)
admin.site.register(Job)
admin.site.register(Skill)
admin.site.register(UserVolunteer)
admin.site.register(UserOwner)
admin.site.register(AreasList)

# Register your models here.
