const express = require('express')
const app = express()
const bodyParser = require('body-parser')

//ConnectWithClient
const http = require('http')
const server = http.Server(app)
const socket = require('socket.io')
const IO = socket(server).listen(3000)
//

app.use(bodyParser.urlencoded({extended:true}))
app.use(bodyParser.json())


app.post('/send', (req, res)=>{
    console.log("send")
    IO.sockets.emit('toClient', req.body)
    return res.status(200).send({success:true})
})

IO.on("connection", function(socket){
    console.log("USER JOIN")

    socket.on('fromClient', function(data){
        socket.emit('toClient', {'msg' : data})
    })
})

server.listen(5000, ()=>console.log("Server is Connected"))