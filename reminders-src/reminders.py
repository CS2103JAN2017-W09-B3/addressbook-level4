#!/usr/bin/python

#@@author A0146789H

# Provides the Reminders API for Suru

import sendgrid
from sendgrid.helpers.mail import *
from flask import Flask, abort, request
from apscheduler.schedulers.background import BackgroundScheduler
import os
import re

app = Flask(__name__)
sg = sendgrid.SendGridAPIClient(apikey=os.environ.get('SENDGRID_API_KEY'))
scheduler = BackgroundScheduler()

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
        print file_data

        return file_data

    abort(404)

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=80)
