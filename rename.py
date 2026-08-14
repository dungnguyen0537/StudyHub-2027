import os
import glob

for f in glob.glob('c:/Mobile-Dev/StudyHub/app/src/main/res/layout/UNKNOWN*.xml'):
    content = open(f, encoding='utf-8').read()
    if 'id="@+id/btnRegister"' in content:
        name = 'fragment_register.xml'
    elif 'id="@+id/btnEditProfile"' in content or 'id="@+id/btnLogout"' in content:
        name = 'fragment_profile.xml'
    elif 'id="@+id/tvContent"' in content and 'id="@+id/tvDate"' in content:
        name = 'item_note.xml'
    elif 'id="@+id/tvTimeRange"' in content or 'id="@+id/tvRoom"' in content:
        name = 'item_schedule.xml'
    elif 'id="@+id/tvCategory"' in content or 'id="@+id/cbCompleted"' in content:
        name = 'item_task.xml'
    else:
        name = 'unresolved_' + os.path.basename(f)
    print(f'Renaming {os.path.basename(f)} to {name}')
    os.rename(f, os.path.join(os.path.dirname(f), name))
