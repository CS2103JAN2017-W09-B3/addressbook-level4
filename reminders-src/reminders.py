#!/usr/bin/python

#@@author A0146789H

# Provides the Reminders API for Suru

import sendgrid
from sendgrid.helpers.mail import *
from flask import Flask, abort, request
from apscheduler.schedulers.background import BackgroundScheduler
import xml.etree.ElementTree
import dateutil.parser
import datetime
import os
import re

app = Flask(__name__)
sg = sendgrid.SendGridAPIClient(apikey=os.environ.get('SENDGRID_API_KEY'))
scheduler = BackgroundScheduler()
email_jobs = {}

FROM_EMAIL = Email("no-reply@suru.com")

def sendmail(email, event, time_delta):
    subject = "[Reminder]: %s" % event
    to_email = Email(email)
    words = "Here's a friendly reminder to attend your '%s'! It's happening in %d minutes!"
    words = words % (event, time_delta)
    content = Content("text/plain", words)
    mail= Mail(FROM_EMAIL, subject, to_email, content)
    response = sg.client.mail.send.post(request_body=mail.get())
    return response

def process(file_data, email, time):
    if email in email_jobs:
        for i in email_jobs[email]:
            i.remove()
        del email_jobs[email]

    jobs = []

    try:
        root = xml.etree.ElementTree.fromstring(file_data)
    except:
        return False

    for task in root.findall("task"):
        name = task.find("name")
        start_date = task.find("startDate")
        completion_status = task.find("completionStatus")
        if completion_status is not None and completion_status.text == "true":
            continue
        if start_date is not None:
            scheduled = dateutil.parser.parse(start_date.text)
            actual = scheduled - datetime.timedelta(minutes=time)
            job = scheduler.add_job(sendmail, "date",
                                    [email, name.text, time],
                                    next_run_time=actual)
            jobs.append(job)

    email_jobs[email] = jobs

@app.route('/')
def index():
    return 'Suru Reminders API'

@app.route('/register/<email>/<int:time>/<action>', methods=["POST"])
def register(email, time, action):
    # Check if the email matches the right format
    email_regex = re.compile(r"(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$)")
    if (not email_regex.match(email)):
        # Abort with a 404 if the regex fails
        abort(404)

    if action == 'disable':
        return "disable " + email

    if action == 'enable':
        # Check if the storage parameter is available
        if 'storage' not in request.files:
            abort(404)

        # Read the data from the file object
        file_data = request.files['storage'].read()

        if (process(file_data, email, time)):
            return "success"
        else:
            abort(404)

    abort(404)

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=80)
