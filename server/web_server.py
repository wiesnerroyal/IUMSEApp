from flask import Flask, request
import base64
import time
import json
import os

#-- wanna be database ---------
username =  "admin"
password =  "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8" # password :D
adminSessionID = ""
adminVoted = False

songTitle1 = "Uptown Funk - Mark Ronson ft. Bruno Mars"
songTitle2 = "Shape of You - Ed Sheeran"
songTitle3 = "Someone Like You - Adele"
songTitle4 = "Old Town Road - Lil Nas X ft. Billy Ray Cyrus"


newsHeader1 = "BREAKING NEWS!"
newsHeader2 = "DAX neuer Rekord!"
newsHeader3 = "Schokolade ist ein Superfood!"
newsHeader4 = "FC Bayern - Titeljagd"

songHistory1 = "11:27"
songHistory2 = "11:32"
songHistory3 = "11:35"
songHistory4 = "11:42"

newsText = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus..."
#------------------------------

ASSETS_DIR = os.path.dirname(os.path.abspath(__file__))
app = Flask(__name__)

def generateSessionID(username):
    id = f"{time.time()}.{username}"
    id = base64.b64encode(id.encode("utf-8")).decode("utf-8")
    return id


def validSession(user, id):
    if (user == username and id == adminSessionID):
        return True
    else:
        return False 


@app.route('/login')
def login():

    user = ""
    passwd = ""
    global adminSessionID

    print(request.headers)
    user = request.args.get("username")
    passwd = request.args.get("password") 
    
    if (user == username and passwd == password):
        adminSessionID = generateSessionID(user)
        return "{'status':'ok', 'sessionid':'%s'}" % (adminSessionID)
    else:
        return "{'status':'nok'}"
    

@app.route("/check_session")
def check_session():

    print(request.headers)
    user = request.args.get("username")
    id = request.args.get("sessionid")

    if (validSession(user, id)):
        return "{'status':'ok'}"
    else:
        return "{'status':'nok'}"  
    

@app.route("/voting")
def voting():

    global adminVoted
    print(request.headers)

    songDict = {}
    songDict['voting'] = "ok"
    songDict['songs'] = [songTitle1, songTitle2, songTitle3, songTitle4]

    if not adminVoted:
        return json.dumps(songDict, indent=4)
    else:
        return "{'voting':'done'}"  
    

@app.route("/vote")
def vote():

    global adminVoted
    print(request.headers)

    song = request.args.get("song")
    print(song)

    adminVoted = True
    return "{'status':'ok'}"

@app.route("/news")
def news():

    newsDict = {}
    newsDict['news'] = "ok"
    newsDict['header'] = [newsHeader1, newsHeader2, newsHeader3, newsHeader4]
    newsDict['content'] = [newsText, newsText, newsText, newsText]
    return json.dumps(newsDict, indent=4)

@app.route("/history")
def history():
    
    historyDict = {}
    historyDict['history'] = "ok"
    historyDict['time'] = [songHistory1, songHistory2, songHistory3, songHistory4]
    historyDict['songs'] = [songTitle1, songTitle2, songTitle3, songTitle4]
    return json.dumps(historyDict, indent=4)


if __name__ == '__main__':
    context=("certificate.pem", "private_key.pem")
    app.run(debug=True, host="localhost", port=8080, ssl_context=context)