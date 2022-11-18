from Car import *


if __name__ == '__main__':
    input("Press Enter to start")

    lon = float(input("input longitude"))
    lat = float(input("input latitude"))
    car_id = int(input("input car id"))
    car = Car(lat, lon, car_id)

    #car.connect_with_server(("localhost", 3000))
    spd = float(input("input speed"))
    delay = float(input("input delay"))
    steps = int(input("input steps"))

    for i in range(steps): # in case more cars should be added
        car.simulate_route(spd, delay)
