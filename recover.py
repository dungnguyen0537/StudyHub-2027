import os
import re

file_path = r"c:\Mobile-Dev\StudyHub\activity_main_backup.xml"
with open(file_path, "r", encoding="utf-8") as f:
    content = f.read()

# Split by <?xml
parts = content.split('<?xml version="1.0" encoding="utf-8"?>')
parts = [p.strip() for p in parts if p.strip()]

layouts = {}
for p in parts:
    xml = '<?xml version="1.0" encoding="utf-8"?>\n' + p
    
    # Identify the file based on content
    if 'tools:context=".activity.MainActivity"' in xml:
        name = 'activity_main.xml'
    elif '@raw/anim_study' in xml:
        name = 'activity_splash.xml'
    elif 'tools:listitem="@layout/item_schedule"' in xml and 'tvSubjectCount' in xml:
        name = 'fragment_dashboard.xml'
    elif 'tools:listitem="@layout/item_deadline"' in xml and 'swipeRefreshLayout' in xml:
        name = 'fragment_deadline_list.xml'
    elif 'tools:listitem="@layout/item_note"' in xml:
        name = 'fragment_note_list.xml'
    elif 'tools:listitem="@layout/item_task"' in xml:
        name = 'fragment_task_list.xml'
    elif 'tools:listitem="@layout/item_subject"' in xml:
        name = 'fragment_subject_list.xml'
    elif 'tools:listitem="@layout/item_schedule"' in xml and 'swipeRefreshLayout' in xml:
        name = 'fragment_schedule.xml'
    elif 'app:title="@string/add_subject"' in xml:
        name = 'fragment_add_subject.xml'
    elif 'app:title="@string/nav_tasks"' in xml and 'swipeRefreshLayout' not in xml:
        name = 'fragment_task_container.xml'
    elif 'text="@string/forgot_password_desc"' in xml:
        name = 'fragment_forgot_password.xml'
    elif 'text="@string/login"' in xml and 'btnGoogleLogin' in xml:
        name = 'fragment_login.xml'
    elif 'text="@string/register_desc"' in xml:
        name = 'fragment_register.xml'
    elif 'tvEmail' in xml and 'btnEditProfile' in xml:
        name = 'fragment_profile.xml'
    elif 'id="@+id/cbCompleted"' in xml and 'id="@+id/tvDate"' in xml and 'id="@+id/tvTime"' in xml:
        name = 'item_deadline.xml'
    elif 'id="@+id/tvDate"' in xml and 'id="@+id/tvType"' in xml:
        name = 'item_note.xml'
    elif 'id="@+id/tvTimeRange"' in xml and 'id="@+id/tvRoom"' in xml:
        name = 'item_schedule.xml'
    elif 'id="@+id/tvCredits"' in xml and 'id="@+id/tvTeacher"' in xml:
        name = 'item_subject.xml'
    elif 'id="@+id/cbCompleted"' in xml and 'id="@+id/tvCategory"' in xml:
        name = 'item_task.xml'
    else:
        name = 'UNKNOWN_' + str(hash(xml)) + '.xml'
        
    layouts[name] = xml

print("Found files:", list(layouts.keys()))
if len(layouts) == 19:
    print("Successfully identified all 19 files!")
    for name, xml in layouts.items():
        with open(os.path.join(r"c:\Mobile-Dev\StudyHub\app\src\main\res\layout", name), "w", encoding="utf-8") as f:
            f.write(xml)
    print("Files recovered.")
else:
    print("Missing some files or misidentified!")
