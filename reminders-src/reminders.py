#!/usr/bin/python

#@@author A0146789H

# Provides the Reminders API for Suru

import sendgrid
from sendgrid.helpers.mail import *
from flask import Flask, abort, request
import os
import re

app = Flask(__name__)
sg = sendgrid.SendGridAPIClient(apikey=os.environ.get('SENDGRID_API_KEY'))

@app.route('/')
def index():
    return 'Suru Reminders API'

# For reference.
# @app.route('/sendmail')
# def sendmail():
    # from_email = Email("no-reply@suru.com")
    # subject = "[Reminder]: Software Engineering Lecture"
    # to_email = Email("")
    # content = Content("text/plain", "Here's a friendly reminder to attend your 'Software Engineering Lecture'! It's happening in 15 minutes!")
    # mail = Mail(from_email, subject, to_email, content)
    # response = sg.client.mail.send.post(request_body=mail.get())
    # reply = str(response.status_code)
    # reply += str(response.body)
    # reply += str(response.headers)
    # return reply

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

        return file_data

    abort(404)

if __name__ == "__main__":
    app.run()
