#!/usr/bin/python

#@@author A0146789H
import sendgrid
from sendgrid.helpers.mail import *
from flask import Flask
import os

app = Flask(__name__)

@app.route('/')
def index():
    return 'Testing App'

@app.route('/sendmail')
def sendmail():
    sg = sendgrid.SendGridAPIClient(apikey=os.environ.get('SENDGRID_API_KEY'))
    from_email = Email("no-reply@suru.com")
    subject = "Testing Sending Using Sendgrid"
    to_email = Email("redacted@redacted.com")
    content = Content("text/plain", "Contents of this test email")
    mail = Mail(from_email, subject, to_email, content)
    response = sg.client.mail.send.post(request_body=mail.get())
    reply = str(response.status_code)
    reply += str(response.body)
    reply += str(response.headers)
    return reply

if __name__ == "__main__":
    app.run()
