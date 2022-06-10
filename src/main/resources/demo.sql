select concat(firstname, ' ', lastname) as name, avg(avarage) from
    (
        select userid , users.firstname, users.lastname, course.course_name, avg(score) as avarage
        from users
                 join usercourses on users.id = usercourses.userid
                 join course on usercourses.courseid = course.id
                 join exams on usercourses.courseid = exams.course_id
            and usercourses.userid = exams.user_id
        where users.id in (select userid from usercourses where courseid = 18)
        group by course_id
    )
group by userid;