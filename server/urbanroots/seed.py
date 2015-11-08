import os
os.environ['DJANGO_SETTINGS_MODULE'] = 'urbanroots.settings'
import django
django.setup()
import datetime
from adminapi.models import Skill, Area, UserVolunteer, SkillsList, AreasList, Job
from django.contrib.auth.models import User

# Add skills and areas

skills = [
    "Gardening",
    "Repair",
    "Lifting heavy objects",
    "Cycling",
    "Painting",
    "Landscaping",
    "Mechnical repairs",
    "Fertilisation",
    "Pest control",
]

areas = [
    "Toryglen",
    "Castlemilk",
    "Govan",
    "Ibrox",
    "Priesthill & Greater Pollok",
    "Pollokshields",
]

Skill.objects.all().delete()
Area.objects.all().delete()

for skill in skills:
    Skill(name=skill).save()

for area in areas:
    Area(name=area).save()

# Add volunteers

volunteers = [
    ("cshtarkov", "Christian Shtarkov"),
    ("ailov", "Alexander Ilov"),
    ("mharling", "Michael Harling"),
    ("ksonev", "Kristian Sonev"),
]

i = 0
rep_names = ["broken fence", 
     "burnt wall",
     "destroyed garden", 
     "bin set on fire", 
     "broken gate", 
     "smashed shed window",
     "wall spray painted"]
rep_descrpts = ["Fence broken next to Castle Road",
      "Wall burnt by vandals next to garden shed",
     "Garden next to Govan Road"
     "bin set on fire next to road at left side of garden",
     "Garden gate broken by vandals",
     "Shed window smashed in garden next to McCulloch Street ",
     "Wall next to garden shed has been spray painted"
     ]
rep_names = [
    "Fence broken by vandals next to garden shed",
    "Wall burnt by vandals next to garden shed",
    "Garden next to Govan Road",
    "Bin set on fire next to road at left side of Priesthill Garden",
]
rep_descrpts = [
    """
    I was walking by the garden shed behind my building in Pollock,
    and I noticed that the roof of the shed has been vandalised.
    There are multiple tears in the roof, probably made a sharp blade,
    not sure why.
    The crops themselves seem intact.
    """,
    """
    One of the walls of the shed in Ibrox has been reduced to ashes.
    I heard some loud noises from people last night, obviously drunk,
    and talking about setting something on fire. Unfortunately I'm
    not able to indentify any one of them.
    """,
    """
    There's a good opportunity to start a community garden right
    next to Govan Road, towards the supermarket. The lot opposite
    is just sitting there and I don't think anyone would mind.
    """,
    """
    Just this minute I noticed two kids settings fire to the bin
    near Priesthill Garden. If this spreads, it could be trouble.
    """,
]

User.objects.all().delete()
UserVolunteer.objects.all().delete()
Job.objects.all().delete()

for volunteer in volunteers:

    u = User.objects.create_user(volunteer[0], 'blank@what.com', '123')
    u.first_name = volunteer[1].split()[0]
    u.last_name = volunteer[1].split()[1]
    u.save()
    v = UserVolunteer.objects.get_or_create(user=u,
                                            phone_number="123456789", skills="gardening, painting",
                                            address="101 Govan Road")
    skill0 = Skill.objects.get(name=skills[0])
    skill1 = Skill.objects.get(name=skills[1])
    skill_list0 = SkillsList.objects.get_or_create(volunteer=v[0],
                                                   skill=skill0)
    skill_list1 = SkillsList.objects.get_or_create(volunteer=v[0],
                                                   skill=skill1)
    job = Job.objects.get_or_create(name=rep_names[i],accepted=False, created=datetime.datetime.today()
                                    , description=rep_descrpts[i], location=Area.objects.get(name="Ibrox"))
    i+=1
    #for skill_list in SkillsList.objects.all():
        #print(skill_list.volunteer.user.first_name, skill_list.skill.name)

    area0 = Area.objects.get(name=areas[0])
    area_list0 = AreasList.objects.get_or_create(volunteer=v[0],
                                                  area=area0)

    #for area_list in AreasList.objects.all():
        #print(area_list.volunteer.user.first_name, area_list.area.name)
    
