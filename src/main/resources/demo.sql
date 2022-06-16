select concat(firstname, ' ', lastname) as name, avg(avarage) from
    (
        select userid , users.firstname, users.lastname, course.course_name, credit, sum(IfNULL(score,0))/3 as avarage
        from users
                 right join usercourses on users.id = usercourses.userid
                 left join course on usercourses.courseid = course.id
                 left join exams on usercourses.courseid = exams.course_id
            and usercourses.userid = exams.user_id
        where users.id in (select userid from usercourses where courseid = 18)
        group by course_id
    )
group by userid;