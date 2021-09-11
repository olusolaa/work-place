# work-place
This app help an organisation can keep track of their employees record.
## Admin can:
- Add, view, update and delete an employee's record
- Track late comers and absentees.
- View salary records of all employee.

## Admin's login:
- Password: admin
- Email: admin@2

## Employee's login:
-  HR (admin) adds a new staff to the workplace (firstname, lastname, email and gender).
-  HR gives the generated ID and email to the staff (check the ID from "view all employee")
-  Staff activates his account (set password) using ID and email gotten from admin.
-  Staff logs in

## Mark Attendance:
- Attendance marked between 8AM and 9AM is punctual (observe that "Is Late" field changes to false)
- Attendance cannot be marked before 8AM (you'll receive a message that it is too early)
- Attendance cannot be marked after 5PM (you'll receive a message that it is too late)
- Attendance can only be marked once in a day (subsequently, you receive a message that it's already marked)

