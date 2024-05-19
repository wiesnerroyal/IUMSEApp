import socket
import json
import ssl


host = "localhost"
port = 1234
moderator = "Peter"

context = ssl.SSLContext(ssl.PROTOCOL_TLS_SERVER)
context.load_cert_chain(certfile="certificate.pem", keyfile="private_key.pem") 

def socket_server():

    while True:

        sock = socket.socket()
        sock.bind((host, 1234))
        sock.listen(5)

        context = ssl.create_default_context(ssl.Purpose.CLIENT_AUTH)
        context.load_cert_chain(certfile="certificate.pem", keyfile="private_key.pem") 
        context.set_ciphers('AES256+ECDH:AES256+EDH')

        print("Server is running...")
        
        while True:
            conn = None
            ssock, addr = sock.accept()
            print('waiting for a connection')
                
            try:
                conn = context.wrap_socket(ssock, server_side=True)
                print('connection from', addr)

                while True:
                    data = conn.recv(1024).decode()
                    if not data: break
                    data = json.loads(data.replace("'", '"'))
                    msgDict = {}
                    msgDict['status'] = "ok"
                    msgDict['moderator'] = moderator
                    msgDict['msg'] = f"echo {data['msg']}"
                    data = f"{json.dumps(msgDict)} \r\n".encode("utf-8")
                    conn.send(data)
            except ssl.SSLError as e:
                print(e)
            finally:
                conn.close()


if __name__ == '__main__':
    socket_server()