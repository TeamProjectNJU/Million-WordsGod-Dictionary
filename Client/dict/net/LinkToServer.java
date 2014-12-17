package dict.net;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

import net.Message.Message;
import System.UserInfo;
import word.UnionWord;

public class LinkToServer {
	private static String ip = "localhost";
	private static int port = 8000;
	private static int idCreater = 0;
	private static int cardPort = 8005;
	private Socket socket;
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	private String uid;
	private String psw;
	private ObjectOutputStream objOut;
	private static Map requestMap;
	public LinkToServer(Map requestMap){
		try {
			socket = new Socket(ip, port);
			socket.setTcpNoDelay(true);
			//objOut = new ObjectOutputStream(socket.getOutputStream());
			this.requestMap = requestMap;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public LinkToServer(Map requestMap, String uid, String psw){
		this(requestMap);
		this.uid =uid;
		this.psw = psw;
	}
	public void setUid(String uid){
		this.uid = uid;
	}
	public void setPsw(String psw){
		this.psw = psw;
	}
	public boolean login(){
		Message loginMessage = new Message();
		loginMessage.id = idCreater ++;
		loginMessage.reply = false;
		loginMessage.type = Message.LOGIN;
		Message.LoginMsg data = loginMessage.new LoginMsg();
		loginMessage.data = data;
		data.uid = uid;
		data.psw = psw;
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objOut.writeObject(loginMessage);
			objOut.flush();
			objOut.close();
			Message reply = waitReply(loginMessage.id);
			if(reply == null)
				return false;
			return	((Message.ReplyData)(reply.data)).success;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return false;
	}
	public boolean logout(){
		Message logoutMessage = new Message();
		logoutMessage.id = idCreater ++;
		logoutMessage.reply = false;
		logoutMessage.type = Message.LOGOUT;
		Message.LogoutMsg data = logoutMessage.new LogoutMsg();
		logoutMessage.data = data;
		data.uid = uid;
		data.psw = psw;
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objOut.writeObject(logoutMessage);
			objOut.flush();
			objOut.close();
			Message reply = waitReply(logoutMessage.id);
			if(reply == null)
				return false;
			return	((Message.ReplyData)(reply.data)).success;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
		return false;
	}
	public boolean register(String uid, String psw, String email, char sex){
		Message rigisterMsg = new Message();
		rigisterMsg.id = idCreater ++;
		rigisterMsg.reply = false;
		rigisterMsg.type = Message.REGISTER;
		Message.RegsiterMsg data = rigisterMsg.new RegsiterMsg();
		rigisterMsg.data = data;
		data.uid = uid;
		data.psw = psw;
		data.email = email;
		data.sex = sex;
		try {
			System.out.println("sending a register msg");
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objOut.writeObject(rigisterMsg);
			objOut.flush();
			objOut.close();
			System.out.println("sended a register msg");
			Message reply = waitReply(rigisterMsg.id);
			if(reply == null)
				return false;
			return	((Message.ReplyData)(reply.data)).success;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
		return false;
	}
	public boolean changePsw(String newpsw){
		Message changeMsg = new Message();
		changeMsg.id = idCreater ++;
		changeMsg.reply = false;
		changeMsg.type = Message.CHANGE_PSW;
		Message.ChangePsw data = changeMsg.new ChangePsw();
		changeMsg.data = data;
		data.uid = uid;
		data.oldpsw = psw;
		data.newpsw = newpsw;
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objOut.writeObject(changeMsg);
			objOut.flush();
			objOut.close();
			Message reply = waitReply(changeMsg.id);
			if(reply == null)
				return false;
			return	((Message.ReplyData)(reply.data)).success;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
		return false;
	}
	public UnionWord serach(String word){
		Message serachMsg = new Message();
		serachMsg.id = idCreater ++;
		serachMsg.reply = false;
		serachMsg.type = Message.SEARCH;
		Message.Serach data = serachMsg.new Serach();
		serachMsg.data = data;
		data.word = new UnionWord(); 
		data.word.setWordstr(word);
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objOut.writeObject(serachMsg);
			objOut.flush();
			objOut.close();
			Message reply = waitReply(serachMsg.id);
			if(reply == null)
				return null;
			return	((Message.Serach)(reply.data)).word;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
		return null;
		
	}
	public boolean addPrise(String word, int type){
		Message addPriseMsg = new Message();
		addPriseMsg.id = idCreater ++;
		addPriseMsg.reply = false;
		addPriseMsg.type = Message.ADD_PRAISE;
		Message.Add_Praise data = addPriseMsg.new Add_Praise();
		addPriseMsg.data = data;
		data.uid = uid;
		data.psw = psw;
		data.word = word;
		data.source = type;
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objOut.writeObject(addPriseMsg);
			objOut.flush();
			objOut.close();
			Message reply = waitReply(addPriseMsg.id);
			if(reply == null)
				return false;
			return	((Message.ReplyData)(reply.data)).success;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return false;
	}
	public boolean delPrise(String word, int type){
		Message delPriseMsg = new Message();
		delPriseMsg.id = idCreater ++;
		delPriseMsg.reply = false;
		delPriseMsg.type = Message.DEL_PRAISE;
		Message.Del_Praise data = delPriseMsg.new Del_Praise();
		delPriseMsg.data = data;
		data.uid = uid;
		data.psw = psw;
		data.word = word;
		data.source = type;
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objOut.writeObject(delPriseMsg);
			objOut.flush();
			objOut.close();
			Message reply = waitReply(delPriseMsg.id);
			if(reply == null)
				return false;
			return	((Message.ReplyData)(reply.data)).success;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return false;
	}
	public boolean addFriend(String friendUid){
		Message addFriendMsg = new Message();
		addFriendMsg.id = idCreater ++;
		addFriendMsg.reply = false;
		addFriendMsg.type = Message.ADD_FRIEND;
		Message.AddFriend data = addFriendMsg.new AddFriend();
		addFriendMsg.data = data;
		data.uid = uid;
		data.psw = psw;
		data.friend_uid = friendUid;
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objOut.writeObject(addFriendMsg);
			objOut.flush();
			objOut.close();
			Message reply = waitReply(addFriendMsg.id);
			if(reply == null)
				return false;
			return	((Message.ReplyData)(reply.data)).success;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return false;
	}
	public boolean delFriend(String friendUid){
		Message delFriendMsg = new Message();
		delFriendMsg.id = idCreater ++;
		delFriendMsg.reply = false;
		delFriendMsg.type = Message.DEL_FRIEND;
		Message.DelFriend data = delFriendMsg.new DelFriend();
		delFriendMsg.data = data;
		data.uid = uid;
		data.psw = psw;
		data.friend_uid = friendUid;
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objOut.writeObject(delFriendMsg);
			objOut.flush();
			objOut.close();
			Message reply = waitReply(delFriendMsg.id);
			if(reply == null)
				return false;
			return	((Message.ReplyData)(reply.data)).success;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return false;
	}
	public boolean sendText(String text){
		Message sendTextMsg = new Message();
		sendTextMsg.id = idCreater ++;
		sendTextMsg.reply = false;
		sendTextMsg.type = Message.SEND_MESSAGE;
		Message.Send_Message data = sendTextMsg.new Send_Message();
		sendTextMsg.data = data;
		data.uid = uid;
		data.psw = psw;
		data.dialoge = text;
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objOut.writeObject(sendTextMsg);
			objOut.flush();
			objOut.close();
			Message reply = waitReply(sendTextMsg.id);
			if(reply == null)
				return false;
			return	((Message.ReplyData)(reply.data)).success;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return false;
	}
	public boolean sendCard(BufferedImage image, String uid){
		Message cardMsg = new Message();
		cardMsg.id = idCreater ++;
		cardMsg.reply = false;
		cardMsg.type = Message.SEND_CARD;
		Message.Send_Card data = cardMsg.new Send_Card();
		cardMsg.data = data;
		data.uid = uid;
		data.psw = psw;
		data.card = Message.imageToBytes(image);
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objOut.writeObject(cardMsg);
			objOut.flush();
			objOut.close();
			Message reply = waitReply(cardMsg.id);
			if(reply == null)
				return false;
			else{
				String ip = ((Message.IpData)(reply.data)).Ip;
				if(ip == null || ip.length() == 0)
					return false;
				else{
					Socket cardSocket = new Socket(ip, cardPort);
					ObjectOutputStream cardStream = new ObjectOutputStream(cardSocket.getOutputStream());
					data.psw = null;//不能把密码发送出去
					cardStream.writeObject(cardMsg);
					cardStream.flush();
					cardStream.close();
					socket.close();
					return true;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
		return false;
	}
	public ArrayList<UserInfo> getOnlineFriend(){
		
		Message onlineMsg = new Message();
		onlineMsg.id = idCreater ++;
		onlineMsg.reply = false;
		onlineMsg.type = Message.UPDATE_FRIEND_ONLINE;
		Message.OnlineFriend data = onlineMsg.new OnlineFriend();
		onlineMsg.data = data;
		data.friendList = null;
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objOut.writeObject(onlineMsg);
			objOut.flush();
			objOut.close();
			Message reply = waitReply(onlineMsg.id);
			if(reply == null)
				return null;
			return	((Message.OnlineFriend)(reply.data)).friendList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
		return null;
		
	}
	public UserInfo getDetail(){
		Message userInfoMsg = new Message();
		userInfoMsg.id = idCreater ++;
		userInfoMsg.reply = false;
		userInfoMsg.type = Message.USER_INFO;
		Message.Info data = userInfoMsg.new Info();
		userInfoMsg.data = data;
		data.myself = null;
		try {
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objOut.writeObject(userInfoMsg);
			objOut.flush();
			objOut.close();
			Message reply = waitReply(userInfoMsg.id);
			if(reply == null)
				return null;
			return	((Message.Info)(reply.data)).myself;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
		return null;
	}
	private Message waitReply(int id){
		int loop = 0;//time counter
		while(true){
			synchronized(requestMap){
				Message reply = (Message) requestMap.get(id);
				if(reply != null){
					requestMap.remove(id);
					return reply;
				}else if(loop > 100)//10000ms 超时 10s
					return null;
			}
			try {
				Thread.sleep(100);
				loop ++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}