/* Description: userView list组件
   Date: 20210822
 */
import React, {Component, useEffect, useState} from 'react';
import {
  ListItem,
  Button,
  Icon,
  BottomSheet,
  Avatar
} from 'react-native-elements';
import {Text, View, StyleSheet} from "react-native";
import ImagePicker from 'react-native-image-crop-picker';
import {getUserInfo, upload} from "../service/UserService";
import {showMessage} from "react-native-flash-message";
import storage from "./LocalStorage";
import message from "react-native-message/index";
import {useNavigation, useRoute} from "@react-navigation/native";
import {getFundView} from "../service/FundService";

const UserSettingViewList = () => {
  const [isVisible, setIsVisible] = useState(false); // bottomSheet可见性
  const [nickname, setNickname] = useState('');
  const [avatar, setAvatar] = useState('');
  const [introduction, setIntroduction] = useState('');
  const [email, setEmail] = useState('');
  const [riskLevel, setRiskLevel] = useState(3);
  let defaultAvatar = require('../../resources/image/avatar.jpg');

  const navigation = useNavigation();
  const route= useRoute();

  // useEffect(() => {
  //   const unsubscribe = navigation.addListener('focus', () => {
  //     console.log('UserSettingViewList focus')
  //     storage.load({
  //       key: 'loginState',
  //     }).then(ret => {
  //       console.log("ret", ret.nickname)
  //       if (ret.nickname != undefined) {
  //         setNickname(ret.nickname);
  //       }
  //       if (ret.avatar != undefined) {
  //         setAvatar('data:' + ret.avatar);
  //       }
  //       if (ret.email != undefined) {
  //         setEmail(ret.email);
  //       }
  //       if (ret.introduction != undefined) {
  //         setIntroduction(ret.introduction);
  //       }
  //       if (ret.riskLevel != undefined) {
  //         setRiskLevel(ret.riskLevel);
  //       }
  //     }).catch(err => {
  //           console.log(err);
  //         }
  //     );
  //   });
  //   // return unsubscribe;
  // }, [navigation]);

  useEffect(() => {
    storage.load({
      key: 'loginState',
    }).then(ret => {
      if (ret.nickname != undefined) {
        setNickname(ret.nickname);
      }
      if (ret.avatar != undefined) {
        setAvatar('data:' + ret.avatar);
      }
      if (ret.email != undefined) {
        setEmail(ret.email);
      }
      if (ret.introduction != undefined) {
        setIntroduction(ret.introduction);
      }
      if (ret.riskLevel != undefined) {
        setRiskLevel(ret.riskLevel);
      }
    }).catch(err => {
          console.log(err);
        }
    );
  });

  //监听子页面返回传参
  useEffect(()=>{
    if(route.params?.status==undefined || route.params?.message==undefined) {
      return;
    }
    if(route.params?.status==0) {
      message.success(route.params?.message);
    } else {
      message.error(route.params?.message);
    }
  },[route.params?.timestamp]);


  const list = [
    {
      title: '头像',
    },
    {
      title: '昵称',
    },
    {
      title: '简介',
    },
    {
      title: '邮箱',
    },
    {
      title: '风险等级',
    }
  ];

  const list1 = [
    {
      title: '修改密码',
    },
  ];

  const bottomList = [
    {
      title: '拍照',
      titleStyle: {
        textAlign: 'center',
        fontSize: 16,
      },
      onPress: () => {
        ImagePicker.openCamera({
          width: 500,
          height: 500,
          cropping: true,
          cropperCircleOverlay: true,
        }).then(image => {
          console.log(' 图片路径：');
          console.log(image);
          if (image && image.path) {
            upload(image.path, uploadCallback);
          } else {
            setIsVisible(false);
          }
        });
      }
    },
    {
      title: '从相册获取',
      titleStyle: {
        textAlign: 'center',
        fontSize: 16,
      },
      onPress: () => {
        ImagePicker.openPicker({
          width: 500,
          height: 500,
          cropping: true,
          cropperCircleOverlay: true,
        }).then(image => {
          console.log(' 图片路径：');
          console.log(image);
          if (image && image.path) {
            upload(image.path, uploadCallback);
          } else {
            setIsVisible(false);
          }
        });
      }
    },
    {
      title: '取消',
      titleStyle: {
        color: '#be5751',
        textAlign: 'center',
        fontSize: 16,
      },
      onPress: () => setIsVisible(false),
    }

  ]

  function uploadCallback(data) {
    console.log("callback called!");
    console.log(data.status);
    if (data.status == 0) {
      message.success("图片上传成功!");
    } else {
      message.error("图片上传失败!");
    }
    setIsVisible(false);
  }

  const renderChevron = (key) => {
    if (key == 3) {
      return (
          <Text style={{flex: 0.4}}>

          </Text>
      );
    }
    return <ListItem.Chevron/>;
  }
  const renderAvatar = () => {
    if (avatar && avatar != '' && avatar!='data:') {
      let str = 'data:image/png;base64,' + avatar.substring(5);
      return {uri: str};
    } else {
      return defaultAvatar;
    }
  }

  function renderIntroduction() {
    console.log("render Introduction", introduction);
    if(introduction=='') {
      return "他很懒，什么都没有留下";
    }
    else {
      return introduction;
    }
  }

  const renderRightContent = (key) => {
    if (key == 0) {
      return (
          <View style={{flexDirection:'row'}}>
          <Avatar
              size="small"
              rounded
              source={renderAvatar()}
          />
      <Text style={[style.rightText,{paddingTop:'4%',paddingLeft:10}]}>
        修改
      </Text>
          </View>
      );
    }
    if (key == 1) {
      return (
          <Text style={style.rightText}>
            {nickname}
          </Text>
      )
    }
    if (key == 2) {
      return (
          <Text
              style={style.rightText}
              numberOfLines={1}
              ellipsizeMode="tail"
          >
            {renderIntroduction()}
          </Text>
      )
    }
    if (key == 3) {
      return (
          <Text
              style={style.rightText}
              numberOfLines={1}
              ellipsizeMode="tail"
          >
            {email}
          </Text>
      )
    }
    if (key == 4) {
      let text="";
      switch (riskLevel) {
        case 1:
          text = "C1-保守型";
          break;
        case 2:
          text = "C2-稳健型";
          break;
        case 3:
          text = "C3-平衡型";
          break;
        case 4:
          text = "C4-积极型";
          break;
        case 5:
          text = "C5-激进型";
          break;
        default:
          text = "尚未评测";
      }
      return (
          <Text style={style.rightText}>
            {text}
          </Text>
      )
    }

  }

  function renderPress(key) {
    if (key == 0) {
      setIsVisible(true);
      return;
    }
    if (key == 1) {
      navigation.navigate('SettingNickname', {});
      return;
    }
    if (key == 2) {
      navigation.navigate("SettingIntroduction", {introduction: introduction});
    }
    if (key == 4) {
      if(riskLevel==0) {
        navigation.navigate("RiskAnalysis",{});
      }
      else {
        navigation.navigate("RiskAnalysisResult",{riskLevel: riskLevel});
      }
    }
    if (key == 10) {
      navigation.navigate('SettingPassword', {});
    }
  }

  const onGoBack = (data) => {
    console.log(data);
  }

  return (
      <View>
        <View style={{paddingTop: 20}}>
          {
            list.map((item, i) => (
                <ListItem key={i} bottomDivider
                          onPress={() => renderPress(i)}
                          containerStyle={{display: 'flex'}}
                >
                  <ListItem.Content style={{flex: 2}}>
                    <Text style={{
                      fontSize: 16,
                      paddingLeft: 10
                    }}>{item.title}</Text>
                  </ListItem.Content>
                  <ListItem.Content style={{alignItems: 'flex-end', flex: 5}}>
                    {renderRightContent(i)}
                  </ListItem.Content>
                  {renderChevron(i)}
                </ListItem>
            ))
          }
        </View>
        <View style={{paddingTop: 20}}>
          {
            list1.map((item, i) => (
                <ListItem
                    key={i}
                    onPress={() => renderPress(i + 10)}
                    containerStyle={{display: 'flex'}}
                    bottomDivider>
                  <ListItem.Content  style={{flex: 2}}>
                    <Text style={{
                      fontSize: 16,
                      paddingLeft: 10
                    }}>{item.title}</Text>
                  </ListItem.Content>
                  <ListItem.Chevron style={{alignItems: 'flex-end', flex: 5}}/>
                </ListItem>
            ))
          }
        </View>
        <BottomSheet
            isVisible={isVisible}
        >
          {bottomList.map((l, i) => (
              <ListItem key={i} onPress={l.onPress} bottomDivider>
                <ListItem.Content style={{alignItems: 'center'}}>
                  <Text style={l.titleStyle}>{l.title} </Text>
                </ListItem.Content>
              </ListItem>
          ))}

        </BottomSheet>
      </View>
  );

}

const style = StyleSheet.create({
  rightText: {
    color: '#a4a4a4',
  },
})

export default UserSettingViewList;

