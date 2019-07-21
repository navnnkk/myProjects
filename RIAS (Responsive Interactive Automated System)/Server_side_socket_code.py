import subprocess as sp
import socket as sk

s = sk.socket()
s.setsockopt(sk.SOL_SOCKET, sk.SO_REUSEADDR, 1)

ip = "192.168.43.217"
port = 3333
s.bind((ip, port))
s.listen()

csession, caddr = s.accept()

while True:
	data = csession.recv(1024)
	cmd = data.decode()
	if 'useradd' in cmd:
		file1 = open("/root/ansible_playbooks/user.yml", "w")
		csession.send("user name: ".encode())
		usr_name = csession.recv(1024)
		file1.write(f"user_name: {usr_name.decode()}\n")
		csession.send("password: ".encode())
		usr_passwd = csession.recv(1024)
		file1.write(f"password: {usr_passwd.decode()}\n")
		scode, out = sp.getstatusoutput(f"useradd {usr_name.decode()}")
		if scode != 0:
			csession.send("user already exist".encode())
		else:
			csession.send("user successfully created".encode())
		file1.close()
	elif 'mail' in cmd:
		file1 = open("/root/ansible_playbooks/user.yml", "w")
		csession.send("Enter your mail id: ".encode())
		my_mail = csession.recv(1024)
		file1.write(f"my_mail_id: {my_mail.decode()}\n")
		csession.send("Enter your password: ".encode())
		my_pass = csession.recv(1024)
		file1.write(f"my_passwd: {my_pass.decode()}\n")
		csession.send("Enter receiver's mail id: ".encode())
		rec_mail = csession.recv(1024)
		file1.write(f"rec_mail_id: {rec_mail.decode()}\n")
		file1.close()
		ans_cmd = csession.recv(1024)
		ans_decode = ans_cmd.decode()
		output = sp.getoutput(ans_decode)
		print(ans_decode)
		csession.send(output.encode())
	else:
		print(cmd)
		output = sp.getoutput(cmd)
		csession.send(output.encode())
csession.close()
