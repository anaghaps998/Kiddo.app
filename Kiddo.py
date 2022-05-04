import datetime
import demjson as demjson
from requests_html import HTMLSession
import requests
session = HTMLSession()
from flask import Flask, request, jsonify, session


from DBConnection import Db
app=Flask(__name__)

@app.route('/login', methods=['post'])
def login():
    username= request.form['u']
    password = request.form['p']
    d=Db()
    qry= "SELECT * FROM login WHERE Username='"+username+"' AND PASSWORD='"+password+"'"
    res = d.selectOne(qry)
    data={}
    if res is not None:
        if res["UserType"]=="parent":
            return jsonify(status="ok",lid=res["LID"],type="parent")
        else:
            sele="SELECT * FROM `child` WHERE `clid`='"+str(res["LID"])+"'"
            dd=d.selectOne(sele)
            if dd is not None:
                if dd["status"]=="running":
                    return jsonify(status="ok", lid=res["LID"], type="child")
                else:
                    return jsonify(status="no")
            else:
                return jsonify(status="no")
    else:

        return jsonify(status="no")
@app.route('/changepwd', methods=['post'])
def changepwd():
    user=request.form['username']
    currentpwd=request.form['current_password']
    newpwd = request.form['new_password']
    confirmpwd = request.form['confirm_password']
    uid = request.form['id']
    d=Db()
    g=d.selectOne("SELECT * FROM login WHERE Password='"+currentpwd+"' AND Username='"+user+"'")
    res = {}
    if g is not None:

        if newpwd==confirmpwd:
            qry="UPDATE login SET Password='"+newpwd+"' WHERE Password='"+currentpwd+"' and Username='"+user+"'"
            res=d.update(qry)
            return jsonify(status='ok')
        else:
            return jsonify(status='no')
    else:
        return jsonify(status='no')



@app.route('/parentReg',methods=['post'])
def parentReg():
    pname=request.form['name']
    photo=request.files['pic']
    date = datetime.datetime.now().strftime("%y%m%d-%H%M%S")
    photo.save(r"C:\\Users\\ADMIN\\Desktop\\Set\\Kiddo\\static\\parent\\" + date + '.jpg')
    ss = "/static/parent/" + date + '.jpg'
    gender=request.form['gender']
    age = request.form['age']
    phone = request.form['phno']
    email = request.form['email']
    place = request.form['place']
    post = request.form['post']
    pin = request.form['pin']
    password = request.form['pwd']

    d=Db()
    s = d.insert("INSERT INTO login(Username,Password,UserType) VALUES ('" + email + "','" + password + "','parent')")
    qry=d.insert("INSERT INTO parent(pid,pname,photo,gender,age,phone,email,place,post,pin,clid) VALUES ('"+str(s)+"','"+pname+"','"+str(ss)+"','"+gender+"','"+age+"','"+phone+"','"+email+"','"+place+"','"+post+"','"+pin+"','"+str(s)+"')")
    # si = d.insert(s)
    # res = d.insert(qry)
    return jsonify(status='ok')

@app.route('/AddChild',methods=['post'])
def AddChild():
    parentid=request.form['id']
    cname = request.form['name']
    cphoto = request.files['pic']
    date = datetime.datetime.now().strftime("%y%m%d-%H%M%S")
    cphoto.save(r"C:\\Users\\ADMIN\\Desktop\\Set\\Kiddo\\static\\child\\" + date + '.jpg')
    ss="/static/child/"+date+'.jpg'
    cgender = request.form['gender']
    cage = request.form['age']
    cclass = request.form['standard']
    school = request.form['school']
    un=request.form['username']
    pwd = request.form['pwd']
    d=Db()
    s = "INSERT INTO login(Username,Password,UserType) VALUES ('" + un + "','" + pwd + "','child')"
    print(s)
    si=d.insert(s)
    qry = "INSERT INTO child(chid, pid,cname,age,photo,gender,class,school,username,clid) VALUES ('"+str(si)+"','" + parentid + "','" + cname + "','" + cage + "','" + str(ss) + "','" + cgender + "','" + cclass + "','" + school + "','" + un + "','" + str(si) + "')"
    print(qry)
    res = d.insert(qry)
    return jsonify(status='ok')



@app.route('/AddInterests',methods=['post'])
def AddInterests():
    childid = request.form['id']
    type = request.form['interest']
    description = request.form['text']
    d=Db()
    qry="INSERT INTO interests(chid,tagtype,answer) VALUES('" + childid + "','" + type + "','" + description + "')"
    print(qry)
    res=d.insert(qry)
    return jsonify(status='ok')

@app.route('/updateChild',methods=['post'])
def updateChild():
    childid = request.form['chid']
    type = request.form['type']

    print(childid,type)

    d=Db()
    qry="UPDATE `child` SET `status`='"+type+"' WHERE `chid`='"+childid+"'"
    print(qry)
    res=d.update(qry)
    return jsonify(status='ok')



@app.route('/viewChild',methods=['post'])
def viewChild():
    id=request.form['id']
    d=Db()
    qry="SELECT * FROM child WHERE pid='"+id+"'"
    print(qry)
    res=d.select(qry)
    print(res)
    return jsonify(status='ok',data=res)

@app.route('/deleteChild',methods=['post'])
def deleteChild():
    id=request.form['id']
    d=Db()
    qry="DELETE FROM `child` WHERE chid='"+id+"'"
    res=d.select(qry)
    return jsonify(status='ok')

@app.route('/ChildViewProfile',methods=['post'])
def ChildViewProfile():
    lid = request.form['lid']
    db = Db()
    qry = "SELECT child.* FROM child,parent WHERE child.pid=parent.pid  and child.clid='"+lid+"'"
    res = db.selectOne(qry)
    print(qry)
    return jsonify(status='ok',cname=res["cname"],age=res["age"],photo=res["photo"],gender=res["gender"],classs=res["class"],school=res["school"],email=res["username"])


@app.route('/viewInterests',methods=['post'])
def viewInterests():
    id = request.form['id']
    d = Db()
    qry = "SELECT * FROM interests WHERE chid='" + id + "'"
    print(qry)
    res = d.select(qry)
    print(res)
    return jsonify(status='ok', data=res)

# @app.route('/viewVideos',methods=['post'])
# def viewVideos():
#     lid=request.form['id']
#     print("sss",lid)
#     pid=request.form['pid']
#     db=Db()
#     ss=db.select("SELECT tagtype  from interests where chid='"+lid+"'")
#     print(ss)
#

    # q = str(ss)
    # for i in ss:
    #     print(i)
    #     p=q.split(':')
    #     print(p)
    #
    #     url = "https://www.youtube.com/results?search_query=" +q
    #
    #     response = session.get(url)
    #     response.html.render(sleep=1, keep_page=True, scrolldown=2)
    #
    #     for links in response.html.find('a#video-title'):
    #         res1 = next(iter(links.absolute_links))
    #         res={}
    #         if ss:
    #             res['status']="ok"
    #             res['link']=res1
    #             res['data']=ss
    #             return demjson.encode(res)
    #         else:
    #                 res['status'] = ""
    #                 return demjson.encode(res)

    # return jsonify(status="no")

@app.route("/viewVideos",methods=['POST'])
def viewVideos():
    tag=request.form["tagname"]
    answer=request.form["answer"]
    from youtubesearchpython import VideosSearch
    videosSearch = VideosSearch(answer, limit=10)
    ap = videosSearch.result()
    print(ap)
    aa=[]
    for i in ap['result']:
        print("---------------------------------------")
        print(i['title'])
        print(i['link'], "hiii")
        print(i['viewCount']['text'])
        print(i['thumbnails'][0]['url'])
        print(i['publishedTime'])
        print(i['duration'])
        print(i['richThumbnail'])
        tt=i["descriptionSnippet"]
        so=""
        if tt is not None:
            for ii in tt:
                so+=str(ii["text"]) +" "
            print(type(so),"****************************************",so)
            p=aa_check(so)
            if p=="ok":
                s = {"title": i["title"], "link": i["link"], "publishedtime": i['publishedTime'],"id":i["id"],
                     "thumbnails": i['thumbnails'][0]['url'], "duration": i["duration"],"description":so}
                aa.append(s)

    if len(aa)>0:
        return jsonify(status="ok", data=aa)
    else:
        return jsonify(status="no")

a=['fuck', 'piss off', 'bugger off', 'bloody hell', 'bastard', 'wanker', 'tosser', 'bollocks', 'shit', 'fuck you',
     'dick head', 'asshole', 'son of a bitch', 'bitch', 'sex', 'abuse', 'rape', 'murder', 'homicide', 'suicide', 'damn',
     'cunt', 'choad', 'crikey', 'twat', 'nigga']
def aa_check(description):
    cnt=0
    for i in a:
        if i in description:
            cnt+=1
    if cnt==0:
        return "ok"
    else:
        return "no"
@app.route("/viewVideos_search",methods=['POST'])
def viewVideos_search():
    search=request.form["search"]
    tag=request.form["tagname"]
    answer=request.form["answer"]
    from youtubesearchpython import VideosSearch
    videosSearch = VideosSearch(answer, limit=30)
    ap = videosSearch.result()
    print(ap)
    aa=[]
    for i in ap['result']:
        print("---------------------------------------")
        print(i['title'])
        print(i['link'], "hiii")
        print(i['viewCount']['text'])
        print(i['thumbnails'][0]['url'])
        print(i['publishedTime'])
        print(i['duration'])
        print(i['richThumbnail'])
        tt=i["descriptionSnippet"]
        so=""
        if tt is not None:
            for ii in tt:
                so+=str(ii["text"]) +" "
            print(type(so),"****************************************",so)
            p=aa_check(so)
            if p=="ok":
                if search in so or search in i["title"]:
                    s = {"title": i["title"], "link": i["link"], "publishedtime": i['publishedTime'],"id":i["id"],
                         "thumbnails": i['thumbnails'][0]['url'], "duration": i["duration"],"description":so}
                    aa.append(s)

    if len(aa)>0:
        return jsonify(status="ok", data=aa)
    else:
        return jsonify(status="no")
if __name__=='__main__':

    app.run(host="0.0.0.0",debug=True)
