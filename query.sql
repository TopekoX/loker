INSERT INTO loker.user_types (type_name) VALUES
	 ('Pelamar'),
	 ('Perusahaan');

INSERT INTO loker.users (email,is_active,password,registration_date,update_date,users_type_id) VALUES
	 ('ucup@gmail.com',1,'$2a$12$eLpOeygmItrhkkROh6lBCuxPqkbFuubYUajSY7bhCBd9SPHgrtux6',NULL,NULL,1);

INSERT INTO loker.job_seeker_profile (city,country,employee_type,first_name,last_name,profile_picture_url,resume,state,work_authorization,user_id) VALUES
	 ('Palu','Indonesia',NULL,'Ucup','Topekox',NULL,NULL,NULL,NULL,2);

INSERT INTO loker.recruiter_profile (city,company_name,country,first_name,last_name,profile_picture_url,state,user_id) VALUES
	 ('Palu','CV Angin Ribut','Indonesia','Angin','Ribut',NULL,'Sulawesi Tengah',2);