#  Adapted from https://gist.github.com/genekogan/ebd77196e4bf0705db51f86431099e57
#  Big thanks to @genekogan

import argparse
import sys
import urllib2
import os
import requests


# adapted from http://stackoverflow.com/questions/20716842/python-download-images-from-google-image-search

# def get_soup(url, header):
#     return BeautifulSoup(urllib2.urlopen(urllib2.Request(url, headers=header)), 'html.parser')


def main():
    parser = argparse.ArgumentParser(description='Scrape Google images')
    parser.add_argument('-s', '--search', default='bananas', type=str, help='search term')
    parser.add_argument('-n', '--number', default=10, type=int, help="Number of images to download")
    parser.add_argument('-d', '--directory', default='bananas', type=str, help='Name for directory to store images in')
    args = parser.parse_args()
    if (args.directory == 'bananas'):
        args.directory = args.search

    query = args.search  # raw_input(args.search)
    # max_images = args.num_images
    # save_directory = args.directory
    # image_type = "Action"
    # query = query.split()
    # query = '+'.join(query)
    # url = "https://www.google.com/search?q=" + query + "&source=lnms&tbm=isch&tbs=ift:" + args.type + "&start="
    # header = {
    #     'User-Agent': "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36"
    # }
    # soup = get_soup(url, header)
    # ActualImages = []  # contains the link for Large original images, type of  image
    # print(len(soup.find_all("div", {"class": "rg_meta"})))
    # for a in soup.find_all("div", {"class": "rg_meta"}):
    #     link, Type = json.loads(a.text)["ou"], json.loads(a.text)["ity"]
    #     ActualImages.append((link, Type))
    #     pass
    # if not os.path.exists(os.getcwd()+"/images"):
    #     os.makedirs(os.getcwd()+"/images")
    #     pass
    # for i, (img, Type) in enumerate(ActualImages[0:max_images]):
    #     try:
    #         req = urllib2.Request(img, headers={'User-Agent': header})
    #         raw_img = urllib2.urlopen(req).read()
    #         if len(Type) == 0:
    #             f = open(os.getcwd() + "/images/img" + "_" + str(i) + ".jpg", 'wb')
    #         else:
    #             f = open(os.getcwd() + "/images/img" + "_" + str(i) + "." + Type, 'wb')
    #         f.write(raw_img)
    #         f.close()
    #     except Exception as e:
    #         print("could not load : " + img)
    #         print(e)
    num = args.number
    iter = 0
    links = []
    while(num >= 100):
        r = requests.get("https://api.cognitive.microsoft.com/bing/v5.0/images/search/?q="+args.search+"&count=100&offset="+str(100*iter), headers={'Ocp-Apim-Subscription-Key': 'e1062837789040bc9fe877bcef4b384a'})
        print(r.json()['value'])
        for val in r.json()['value']:
            links.append(val['contentUrl'])
        num -= 100
        iter += 1
        pass
    r = requests.get(
        "https://api.cognitive.microsoft.com/bing/v5.0/images/search/?q=" + args.search + "&count="+str(num)+"&offset=" + str(
            100 * iter), headers={'Ocp-Apim-Subscription-Key': 'e1062837789040bc9fe877bcef4b384a'})
    print(r.json()['value'])
    for val in r.json()['value']:
        links.append(val['contentUrl'])
    print(links)
    header = {
        'User-Agent': "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36"
    }
    for i, img in enumerate(links):
        try:
            req = urllib2.Request(img, headers={'User-Agent': header})
            raw_img = urllib2.urlopen(req).read()
            if not os.path.exists(os.getcwd() + "/images/downloaded/"+args.directory):
                os.makedirs(os.getcwd() + "/images/downloaded/"+args.directory)
            f = open(os.getcwd() + "/images/downloaded/"+args.directory+"/img" + "_" + str(i) + ".jpg", 'wb')
            f.write(raw_img)
            f.close()
        except Exception as e:
            print("could not load : " + img)
            print(e)




if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        pass
    sys.exit()
