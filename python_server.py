import json
import re
from json import JSONDecodeError
import requests
# from bs4 import BeautifulSoup
import mysql.connector
from http.server import BaseHTTPRequestHandler, HTTPServer
from urllib.parse import urlparse, parse_qs

DATABASE_CONFIG = {
    'host': 'localhost',
    'user': 'root',
    'password': '',
    'database': 'otodom',
}

def connect_to_database():
    db = mysql.connector.connect(
        host="localhost",
        user="root",
        password="",
        database="otodom"
    )
    return db

def delete_all_records():
    db = connect_to_database()
    cursor = db.cursor()
    sql = "DELETE FROM offers"
    cursor.execute(sql)
    db.commit()
    db.close()

def insert_data(title, address, cost, rooms, area, image_url, offer_url):
    db = connect_to_database()
    cursor = db.cursor()
    sql = "INSERT INTO offers (title, address, cost, rooms, area, image_url, offer_url) VALUES (%s, %s, %s, %s, %s, %s, %s)"
    val = (title, address, cost, rooms, area, image_url, offer_url)
    cursor.execute(sql, val)
    db.commit()
    db.close()

def print_info(title, address, cost, rooms, area, image_url, offer_url):
    print(f"Title: {title}")
    print(f"Address: {address}")
    print(f"Cost: {cost}")
    print(f"Rooms: {rooms}")
    print(f"Area: {area}")
    print(image_url)
    print(offer_url)
    print("--------------------")

def check_duplicate(url_set):
    if len(url_set) != len(set(url_set)):
        print("Duplicate URLs found!")
    else:
        print("No duplicates found.")
    url_set = list(set(url_set))
    print(len(url_set))

# def extract_information(listings):
#     for listing in listings:
#         title = listing.find("h3", {"data-cy": "listing-item-title"}).text.strip()
#         address = listing.find("p", class_="css-14aokuk e1n6ljqa7").text.strip()
#         price_info = listing.find("div", class_="e1n6ljqa19 css-6vtodn ei6hyam0")
# #         price = price_info.find_all("span", class_="css-1ntk0hg ei6hyam1")
#
#         try:
#             cost = price[0].text.strip()
#         except IndexError:
#             cost = 'N/A'
#
#         try:
#             rooms = price[1].text.strip()
#         except IndexError:
#             rooms = 'N/A'
#
#         try:
#             area = price[2].text.strip()
#         except IndexError:
#             area = 'N/A'
#
#         try:
#             rent = price[3].text.strip()
#         except IndexError:
#             rent = 'N/A'
#
#         print(f"Title: {title}")
#         print(f"Address: {address}")
#         print(f"Cost: {cost}")
#         print(f"Rooms: {rooms}")
#         print(f"Area: {area}")
#         print(f"Rent: {rent}")
#         print("--------------------")


def scrap(url,headers):

    response = requests.get(url, headers=headers)
    html_content = response.text

    # Parse the HTML content with BeautifulSoup
    # soup = BeautifulSoup(html_content, "html.parser")
    # listings = soup.find_all("article", class_="css-jxeap9 e1n6ljqa2")
    # extract_information(listings)

    json_pattern = re.compile(r'\{.*?\}', re.DOTALL)
    json_objects = json_pattern.findall(html_content)

    delete_all_records()

    url_set = set()
    extract_from_json(json_objects, url_set)

    check_duplicate(url_set)

def extract_from_json(json_objects,url_set):
    for json_obj_str in json_objects:

        if "name" not in json_obj_str or "Offer" not in json_obj_str:
            continue
        json_obj_str += '}}'
        try:
            json_obj = json.loads(json_obj_str)
        except JSONDecodeError:
            print(f"Error decoding JSON object: {json_obj_str}")
            continue

        offer_url = json_obj["url"]

        if offer_url in url_set:
            continue

        url_set.add(offer_url)

        # Extract the desired information from the JSON object
        title = json_obj["name"]
        try:
            address = json_obj["itemOffered"]["address"]["streetAddress"]
        except:
            address = "N/A"
        cost = json_obj["price"]
        try:
            rooms = json_obj["itemOffered"]["numberOfRooms"]
        except:
            rooms = "N/A"
        try:
            area = json_obj["itemOffered"]["floorSize"]["value"]
        except:
            area = "N/A"
        if (rooms == "N/A"):
            description = json_obj["itemOffered"]["description"]
            rooms_match = re.search(r'(\d+)\s+pokoj', description)
            if rooms_match:
                rooms = int(rooms_match.group(1))
        if (area == "N/A"):
            description = json_obj["itemOffered"]["description"]
            area_match = re.search(r'(\d+)\s+m²\spowierzchni\s+użytkowej', description)
            if area_match:
                area = int(area_match.group(1))
        offer_url = json_obj["url"]
        image_url = json_obj["image"]

        insert_data(title, address, cost, rooms, area, image_url, offer_url)

        # print_info(title, address, cost, rooms, area, image_url, offer_url)


url = "https://www.otodom.pl/pl/oferty/wynajem/mieszkanie/gdansk?distanceRadius=0&page=1&limit=36&locations=%5Bcities_6-40%5D&by=DEFAULT&direction=DESC&viewType=listing"
headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
}
scrap(url, headers)

class MyRequestHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        parsedURL = urlparse(self.path)
        parsedQuery = parse_qs(parsedURL.query)

        minCost = int(parsedQuery.get('minCost', [0])[0])
        maxCost = int(parsedQuery.get('maxCost', [1000000000])[0])
        minRooms = int(parsedQuery.get('minRooms', [0])[0])
        maxRooms = int(parsedQuery.get('maxRooms', [100])[0])
        minArea = int(parsedQuery.get('minArea', [0])[0])
        maxArea = int(parsedQuery.get('maxArea', [10000])[0])

        mydb = connect_to_database()
        rawSelectedOffers = mydb.cursor()

        question = f"SELECT * FROM offers WHERE cost >= {minCost} AND cost <= {maxCost} AND rooms >= {minRooms} AND rooms <= {maxRooms} AND area >= {minArea} AND area <= {maxArea}"
        rawSelectedOffers.execute(question)

        Offers = []

        for rawOffer in rawSelectedOffers:
            offerWithKeys = {
                "title": rawOffer[1],
                "address": rawOffer[2],
                "cost": rawOffer[3],
                "rooms": rawOffer[4],
                "area": rawOffer[5],
                "image_url": rawOffer[6],
                "offer_url": rawOffer[7]
            }
            Offers.append(offerWithKeys)

        self.send_response(200)
        self.send_header('Content-type', 'application/json')
        self.end_headers()
        self.wfile.write(json.dumps(Offers, ensure_ascii=False, indent=2).encode('utf-8'))

serverHttp = HTTPServer(("localhost", 8081), MyRequestHandler)
serverHttp.serve_forever()