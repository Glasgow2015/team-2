os.environ['DJANGO_SETTINGS_MODULE'] = 'urbanroots.settings'
import django
django.setup()

from adminapi.models import Skill, Area

skills = [
    "Gardening",
    "Repair",
]

areas = [
    "Torgglen",
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
