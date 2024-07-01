INSERT INTO users (id, firstname, lastname, email, birthday, password, registration_date)
VALUES(1, 'user1', 'lastname1', 'user1@user.com', '2001-09-03',
'$2a$10$nxo3YHu0I8CyweGh0TVRFOdDaQdwPGm0pc7fTlDwx8SYdT60AXKOG', '2024-06-30'),
(2, 'user2', 'lastname2', 'user2@user.com', '1997-05-14',
'$2a$10$xIvqDkfgQmuR3Zm6J/sh4eXXboBZmGDSWU6bXaPAkl4Xsa6F1YI4m', '2024-05-11')
ON CONFLICT DO NOTHING;

INSERT INTO user_roles (user_id, role)
VALUES (1, 'USER'), (2, 'ADMIN')
ON CONFLICT DO NOTHING;

select setval('seq1', (SELECT MAX(id) FROM users));