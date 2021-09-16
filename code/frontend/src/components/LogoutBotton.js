import {Button} from 'react-native-elements';
import React, {Component} from 'react';
import storage from "./LocalStorage";
import {logout} from "../service/LoginService";
import message from "react-native-message";

export default class LogoutButton extends Component {
  constructor(props) {
    super(props);
  }

  doLogout=(data)=>{
    if(data.status==undefined || data.status!=200) {
      message.info("登出失败");
    }
    else {
      message.info("登出成功");
      //Todo: 写跳转到登录页
    }
  }

  onPress=()=>{
    storage.remove({
      key:'loginState',
    })
    .then(ret=>{
      console.log("remove loginState completed");
    })
    .catch(err=>{
      console.warn(err.message);
    })

    logout(this.doLogout);
  }

  render() {
    return (
        <Button
            containerStyle={{width:'100%',height:40}}
            buttonStyle={{width:'100%',height:'100%',backgroundColor:'#fefefc'}}
            title="退出登录"
            titleStyle={{color:'#3b3b3b'}}
            onPress={this.onPress}
        />
    );
  }
}
