package Server;

import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Driver {
	static Socket socket = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connect();
	}
	
	public static void Connect() {
		try {
			socket = IO.socket("http://127.0.0.1:3000");
			socket.connect();
			System.out.println("Connected with Server");
			socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
				
				public void call(Object... arg0) {
					// TODO Auto-generated method stub
					socket.emit("fromClient", "1");
				}
			}).on("toClient", new Emitter.Listener() {
				
				@Override
				public void call(Object... arg0) {
					// TODO Auto-generated method stub
					try {
						System.out.println(((JSONObject)arg0[0]).get("msg"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						System.out.println("메시지 받기 실패");
					}
				}
			}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
				
				@Override
				public void call(Object... arg0) {
					// TODO Auto-generated method stub
					socket.emit("disconnect", "1");
				}
			});
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			System.out.println("Connect Error");
		}
		
	}

}
