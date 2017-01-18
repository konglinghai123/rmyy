-- ================================================
-- Template generated from Template Explorer using:
-- Create Trigger (New Menu).SQL
--
-- Use the Specify Values for Template Parameters 
-- command (Ctrl-Shift-M) to fill in the parameter 
-- values below.
--
-- See additional Create Trigger templates for more
-- examples of different Trigger statements.
--
-- This block of comments will not be included in
-- the definition of the function.
-- ================================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		wu_zhijun
-- Create date: 2017-1-8
-- Description:	
-- =============================================
IF (OBJECT_ID('tgr_sys_user_off_online', 'TR') is not null)
	DROP TRIGGER tgr_sys_user_off_online
GO
CREATE TRIGGER tgr_sys_user_off_online
   ON  sec_user_online
   AFTER DELETE
AS 
BEGIN
	--定义变量
	declare @user_id bigint, @id varchar(255), @host varchar(255), @user_agent varchar(255), @system_host varchar(255), @start_timestamp datetime2(7), @last_access_time datetime2(7);
	select @user_id=user_id, @id=id, @host=host, @user_agent=user_agent, @system_host=system_host, @start_timestamp=start_timestamp, @last_access_time=last_access_time from sec_user_online; 
	if (not exists(select USER_ID from sec_user_last_online where user_id=@user_id)) 
		insert into sec_user_last_online(user_id, uid, host, user_agent, system_host, last_login_timestamp, last_stop_timestamp, login_count, total_online_time) values(@user_id, @id, @host, @user_agent, @system_host, @start_timestamp, @last_access_time, 1, DATEDIFF(s, @start_timestamp, @last_access_time));
	else
		update sec_user_last_online set uid=@id, host=@host, user_agent=@user_agent, system_host=@system_host, last_login_timestamp=@start_timestamp, last_stop_timestamp=@last_access_time, login_count=login_count + 1, total_online_time=total_online_time + DATEDIFF(s, @start_timestamp, @last_access_time) where user_id = @user_id;
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for trigger here

END
GO
