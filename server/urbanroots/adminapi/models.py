from django.db import models
from datetime import datetime
from django.contrib.auth.models import User


# Volunteer User reporting or being assigned to jobs
class UserVolunteer(models.Model):

    def __unicode__(self):
        return self.user.username

    user = models.OneToOneField(User)

    # additional attributes
    phone_number = models.CharField(max_length=15, blank=True, null=True)
    address = models.CharField(max_length=128, blank=True, null=True)
    skills = models.CharField(max_length=128, blank=True, null=True)
    accepted = models.BooleanField(default=False)

    # used pass boolean value to view
    assigned = models.BooleanField(default=False)


<<<<<<< HEAD
# Urban Roots Admin
=======
# Urban Roots Administrative user
>>>>>>> f2d05b69443917ac7b20ed0cea5a4f7601024031
class UserOwner(models.Model):

    user = models.OneToOneField(User)

    def __unicode__(self):
        return self.user.username


# General location for jobs
class Area(models.Model):
    name = models.CharField(max_length=128, unique=True)

    def __unicode__(self):
        return self.name


# Job Category
class Category(models.Model):
    name = models.CharField(max_length=128, unique=True)

    def __unicode__(self):
        return self.name


# Represents a job in the system reported by app users and managed by administration
class Job(models.Model):
    name = models.CharField(max_length=128)
    created = models.DateTimeField(default=datetime.now, blank=True, null=True)
    completed = models.DateTimeField(blank=True, null=True)
    accepted = models.BooleanField(default=False)
    latitude = models.DecimalField(max_digits=100, decimal_places=10, blank=True,null=True)
    longitude = models.DecimalField(max_digits=100, decimal_places=10, blank=True, null=True)
    description = models.TextField()
    location = models.ForeignKey(Area, null=True) # this probably shouldn't be null

    # Relationships **************************
    creator = models.ForeignKey(User, blank=True, null=True)

    def __unicode__(self):
        return self.name


# Volunteer Skills
class Skill(models.Model):
    name = models.CharField(max_length=128, unique=True)

    def __unicode__(self):
        return self.name


# Intersecting Entities to resolve Many to Many relationships *************************************

# List of Skills for Volunteers
class SkillsList(models.Model):
    volunteer = models.ForeignKey(UserVolunteer, related_name='volunteer_skill')
    skill = models.ForeignKey(Skill, related_name='skill')

    class Meta:
        unique_together = ('volunteer', 'skill')


# List of areas Volunteers are interested in
class AreasList(models.Model):
    volunteer = models.ForeignKey(UserVolunteer, related_name='volunteer_area')
    area = models.ForeignKey(Area, related_name='area')

    class Meta:
        unique_together = ('volunteer', 'area')


# List of jobs
class JobsList(models.Model):
    volunteer = models.ForeignKey(UserVolunteer, related_name='volunteer_jobs')
    job = models.ForeignKey(Job, related_name='job')

    class Meta:
        unique_together = ('volunteer', 'job')
