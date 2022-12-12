from Car import *


if __name__ == '__main__':
    input("Press Enter to start")

    #lon = float(input("input longitude"))
    #lat = float(input("input latitude"))
    #car_id = input("input car token")
    lat = 1
    lon = 1
    car_id = "dfsdfsdfsdsdf"
    car = Car(lat, lon, car_id)

    car.connect_with_server(("127.0.0.1", 5081))

    #spd = float(input("input speed"))
    #delay = float(input("input delay"))
    #steps = int(input("input steps"))

    spd = 0.0001
    steps = 10
    delay = 4

    for i in range(steps): # in case more cars should be added
        car.simulate_route(spd, delay)
