import {Button, Card, ListItem} from 'react-native-elements'
import React, {Component} from 'react';
import {View, Text} from 'react-native';
import {Avatar} from 'react-native-paper';
import message from 'react-native-message';
import Header from "../components/Header";
import { getUserInfo} from "../service/UserService";
import LogoutButton from "../components/LogoutBotton";
import FacebookExample from "../components/FacebookExample";


export default class UserView extends Component {

  constructor(props) {
    super(props);
  }

  check=()=>{
  }

  render() {
    return (
        <View>
          <Card>
            <Button
                style={{
                  width:'80%',
                  minHeight:50,
                }}
            >
            </Button>
          </Card>
          <View>
            <LogoutButton/>
          </View>
        </View>
    )
  }
}
