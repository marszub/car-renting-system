import random
import socket
import time


class Car:
    def __init__(self, latitude, longitude, car_id):
        self.latitude = latitude
        self.longitude = longitude

        self.id = car_id

        self.status = "open"  # open  or closed

        self.socket = None

    def connect_with_server(self, server_address: tuple):
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket.connect(server_address)

    def send_data_to_server(self):
        message = str(self.id) + ";" + str(self.latitude) + ";" + str(self.longitude)
        print(message)
        self.socket.sendall(bytes(message))

    def simulate_route(self, speed=0.01, delay=10):  # while car is active
        random_direction = (random.randint(-1, 1), random.randint(-1, 1))
        if random_direction[0] == 0 and random_direction[1] == 0:  # would not move
            random_direction = (1, 1)

        self.latitude += speed * random_direction[0]
        self.longitude += speed * random_direction[1]

        time.sleep(delay)

        self.send_data_to_server()

        pass

