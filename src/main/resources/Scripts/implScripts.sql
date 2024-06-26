Create table `ms_doctor_schedule` (
`SCHEDULE_ID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '',			
`DOCTOR_ID` int(10) UNSIGNED NOT NULL COMMENT '',	
`SCHEDULE_DATE` date NOT NULL COMMENT '',			
`START_TIME` time NOT NULL COMMENT '',			
`STOP_TIME` time NOT NULL COMMENT '');


 ALTER TABLE `ms_doctor_schedule` ADD INDEX `fk_doctorSchedule_doctorId` (`DOCTOR_ID` ASC);
ALTER TABLE `ms_doctor_schedule` ADD CONSTRAINT `fk_doctorSchedule_doctorId`  FOREIGN KEY (`DOCTOR_ID`)  
REFERENCES `ms_doctor` (`DOCTOR_ID`)  ON DELETE NO ACTION  ON UPDATE NO ACTION;


ALTER TABLE ms_doctor_schedule
ADD CONSTRAINT fk_doctorSchedule_doctorId
FOREIGN KEY (DOCTOR_ID)
REFERENCES ms_doctor (DOCTOR_ID)
ON DELETE CASCADE
ON UPDATE CASCADE;


INSERT INTO ms_doctor_schedule (DOCTOR_ID, SCHEDULE_DATE,START_TIME,STOP_TIME) values
(1,'2024-06-01','10:00','12:00' ),(1,'2024-06-01','16:00','20:00' ),
(1,'2024-06-02','10:00','12:00' ),
(1,'2024-06-03','10:00','12:00' ),
(1,'2024-06-04','10:00','12:00' ),(1,'2024-06-04','16:00','20:00' ),
(1,'2024-06-05','10:00','12:00' ),
(1,'2024-06-06','10:00','12:00' ),(1,'2024-06-06','16:00','20:00' ),
(1,'2024-06-07','10:00','12:00' );


SELECT d.DOCTOR_ID, u.NAME `DOCTOR_NAME`, ds.SCHEDULE_DATE from ms_doctor d 
LEFT JOIN us_user u ON d.USER_ID=u.USER_ID  LEFT JOIN ms_doctor_schedule ds 
ON d.DOCTOR_ID=1 where d.DOCTOR_ID= 1 AND 
ds.SCHEDULE_DATE = '2024-06-01' ;


SELECT d.DOCTOR_ID, u.NAME `DOCTOR_NAME`, ds.SCHEDULE_DATE FROM ms_doctor d LEFT JOIN 
us_user u ON d.USER_ID=u.USER_ID LEFT JOIN ms_doctor_schedule ds ON d.DOCTOR_ID=1 
WHERE d.DOCTOR_ID=1 AND ds.SCHEDULE_DATE='2024-06-01'group by ds.DOCTOR_ID,ds.SCHEDULE_DATE;


SELECT  ds.START_TIME, ds.STOP_TIME from  ms_doctor_schedule ds where ds.DOCTOR_ID= 1
AND ds.SCHEDULE_DATE = '2024-06-01';

INSERT INTO  mo_user (NAME, USERNAME,PASSWORD,MOBILE_NO,ROLE_ID,ACTIVE) VALUES
('Tony','ironman','','9075225067','Admin',1),('steve','captain','' ,'7972707692','Regular',1);