from django.db import models
from datetime import datetime
from django.contrib.auth.models import User

# Volunteer
class UserVolunteer(models.Model):

    def __unicode__(self):
        return self.user.username

    user = models.OneToOneField(User)

    # additional attributes
    phone_number = models.CharField(max_length=15, blank=True)

    accepted = models.BooleanField(default=False)

    # relationships
    # don't uncomment!
    # skills_list = models.ForeignKey(SkillsList, blank=True, null=True)
    # areas_list = models.ForeignKey(AreasList)


# Urban Roots Admin
class URAdmin(models.Model):

    def __unicode__(self):
        return self.user.username


# Area Name
class Area(models.Model):
    name = models.CharField(max_length=128, unique=True)

    def __unicode__(self):
        return self.name


# Job Category
class Category(models.Model):
    name = models.CharField(max_length=128, unique=True)

    def __unicode__(self):
        return self.name


# Job instance
class Job(models.Model):
    name = models.CharField(max_length=128)
    created = models.DateTimeField(default=datetime.now, blank=True)
    completed = models.DateTimeField(blank=True)
    accepted = models.BooleanField(default=False)
    latitude = models.DecimalField(max_digits=10, decimal_places=10, blank=True)
    longitude = models.DecimalField(max_digits=10, decimal_places=10, blank=True)
    description = models.TextField()

    # Relationships
    creator = models.ForeignKey(User, blank=True, null=True)

    def __unicode__(self):
        return self.name


# Volunteer Skills
class Skill(models.Model):
    name = models.CharField(max_length=128, unique=True)

    def __unicode__(self):
        return self.name


# Intersecting Entities to resolve Many to Many rels

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
