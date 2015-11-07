from django.db import models
from datetime import datetime
from django.contrib.auth.models import User


# Volunteer
#class User_Volunteer(models.Model)


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
    created = models.DateField()
    completed = models.DateField()
    accepted = models.BooleanField()
    latitude = models.DecimalField(max_digits = 10, decimal_places = 10)
    longitude = models.DecimalField(max_digits = 10, decimal_places = 10)
    description = models.TextField()

    def __unicode__(self):
        return self.name


# Volunteer Skills
class Skill(models.Model):
    name = models.CharField(max_length=128, unique=True)

    def __unicode__(self):
        return self.name







