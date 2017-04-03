#!/usr/bin/python

#@@author A0146789H
import sendgrid
import os
from sendgrid.helpers.mail import *
import sys

if len(sys.argv) != 3:
    print "python email-test.py from-email to-email"
    exit()

sg = sendgrid.SendGridAPIClient(apikey=os.environ.get('SENDGRID_API_KEY'))
from_email = Email(sys.argv[1])
subject = "Testing Sending Using Sendgrid"
to_email = Email(sys.argv[2])
content = Content("text/plain", "Contents of this test email")
mail = Mail(from_email, subject, to_email, content)
response = sg.client.mail.send.post(request_body=mail.get())
print response.status_code
print response.body
print response.headers
