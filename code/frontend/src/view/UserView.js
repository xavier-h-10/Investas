//目前不同页面间信息传输方式有两种:第一种采用AsyncStorage,在不同页面调用;第二种采用navigation.navigate传参,不用goBack.
//By hzm 20210831
import {Button, Card, ListItem, Image as MyImage} from 'react-native-elements'
import React, {Component, useEffect, useState} from 'react';
import {
    View,
    Text,
    RefreshControl,
    ScrollView,
    StyleSheet,
    SafeAreaView,
    Dimensions,
    Image
} from 'react-native';
import message from 'react-native-message';
import {getUserInfo} from "../service/UserService";
import MyCard from "../components/MyCard";
import storage from "../components/LocalStorage";
import {logout} from "../service/LoginService";
import {useRoute} from "@react-navigation/native";
import Header from "../components/Header";
import MyStatusBar from "../components/MyStatusBar";
import {getFundView} from "../service/FundService";

const width = Dimensions.get("window").width;
const height = Dimensions.get("window").height;

export default function UserView({route,navigation}) {
    const [refreshing, setRefreshing] = useState(false);
    const [username, setUsername] = useState('');
    const [avatar, setAvatar] = useState('');
    const [loginState, setLoginState] = useState(false);
    const {width, height} = Dimensions.get("window");
    useEffect(() => {
      //此处修改逻辑 存在async storage是给别的页面所用的,因此刷新时一定要重新掉一次api
      getUserInfo(fetchData);
    }, [refreshing]);

    // //监听子页面返回传参
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

    useEffect(() => {
        const unsubscribe = navigation.addListener('focus', () => {
            getUserInfo(fetchData);
        });
        // return unsubscribe;
    }, [navigation]);

    const fetchData = (data) => {
        let tmp = '';
        if (data.status == 0) {
            if (data.data) {
                setUsername(data.data.nickname);
                if (data.data.avatar != undefined && data.data.avatar != null) {
                    tmp = data.data.avatar;
                    setAvatar(tmp);
                }
                storage.save({
                    key: 'loginState',
                    data: {
                        nickname: data.data.nickname,
                        avatar: tmp,
                        email: data.data.email,
                        introduction: data.data.introduction,
                        riskLevel: data.data.riskLevel,
                    }
                }).then(r => {
                    console.log("UserView: loginState stored");
                }).catch(err => {
                    console.log("UserView: loginState not stored ---", err);
                })
            }
            setLoginState(true);
        } else {
            setUsername('');
            setAvatar('');
            setLoginState(false);
        }
    };

    const onRefreshHandle = () => {
        setRefreshing(true);
        setTimeout(() => {
            setRefreshing(false);
        }, 1000);
    }

    const checkUserInfo = (data) => {
        console.log("checkUserInfo called");
        console.log(data);
    }

    const check = () => {
        console.log("onclick called");
        getUserInfo(checkUserInfo);
    }

    const change = (value) => {
        // console.log("change value:", value);
        setRefreshing(value);
    }

    function doLogout(data) {
        if (data == undefined || data.status == undefined || data.status != 200) {
            message.error("登出失败!");
        } else {
            message.success("登出成功!");
        }
    }



    const handleNotLoginNavigate = (direction,naviParam) => {
        getUserInfo((data) => {
            console.log(data);
            console.log("handle not login navigate", data.status);
            if (data.status === 0) {
                console.log("push")
                navigation.push(direction,naviParam);
            } else {
                navigation.navigate('Login');
            }
        })
    }

    const mylist = [
        {
            title: '所有比赛',
            style: {},
            onPress: () => {
                handleNotLoginNavigate('CompeList',null);
            }
        },
        // {
        //     title: '帮助',
        //     style: {},
        //     onPress: () => {
        //         navigation.navigate('Help');
        //     },
        // },
        {
            title: '常见问题',
            style: {},
            onPress: () => {
                navigation.navigate('Question');
            }
        },
        {
            title: '关于',
            style: {},
            onPress: () => {
                navigation.navigate('About');
            },
        },
    ];

    const onPressLogout = () => {
        if (loginState == false) {
            return;
        }
        storage.remove({
            key: 'loginState',
        })
            .then(ret => {
                console.log("remove loginState completed");
            })
            .catch(err => {
                console.warn(err.message);
                message.error("您尚未登录!");
            })
        setLoginState(false);
        logout(doLogout);
    }

    return (
        <SafeAreaView>
            <View style={{backgroundColor:'#fefefe'}}>
            <MyStatusBar/>
            <Image
                source={require("../assets/myview_background.jpg")}
                width={"100%"}
                style={{
                    position:'absolute',
                    top:0,
                }}
            />
            {/*<Header>我的</Header>*/}
            <ScrollView
                refreshControl={
                    <RefreshControl
                        refreshing={refreshing}
                        onRefresh={() => {
                            onRefreshHandle()
                        }}
                    />
                }
                style={{minHeight: height}}
            >
                <View style={style.cardContainer}>
                    <MyCard refreshing={refreshing} username={username} avatar={avatar}
                            loginState={loginState}/>
                </View>


                <MyImage
                    source={require("../assets/competition_1.jpg")}
                    style={{
                        height: 0.10*height,
                        width: 0.90*width,
                        marginLeft: 0.05*width,
                        marginTop: 0.10*height,
                        borderRadius: 20
                    }}
                    onPress={()=>{
                        handleNotLoginNavigate('MyCompeList',null);
                    }}
                    resizeMode="cover"
                />
                <View style={{paddingTop:20}}>
                    <ListItem bottomDivider/>
                </View>
                <View>
                    {
                        mylist.map((item, i) => (
                            <ListItem key={i} bottomDivider onPress={item.onPress}>
                                <ListItem.Content>
                                    <ListItem.Title style={item.style}>{item.title}</ListItem.Title>
                                </ListItem.Content>
                                <ListItem.Chevron/>
                            </ListItem>
                        ))
                    }
                </View>
                    <ListItem onPress={() => onPressLogout()} bottomDivider>
                        <ListItem.Content>
                            <ListItem.Title>退出登录</ListItem.Title>
                        </ListItem.Content>
                        <ListItem.Chevron/>
                    </ListItem>
            </ScrollView>
            </View>
        </SafeAreaView>
    );
}

const style = StyleSheet.create({
    titleStyle: {
        textAlign: 'center',
        fontSize: 16,
    },
    cardContainer: {
        paddingTop: 20,
    },
    imageContainer:{
        width:0.8*width,
        marginLeft:0.1*width,
        marginTop:0.1*width,
        height:0.15*height,
        alignItems:'center',
        borderRadius:30,
        // backgroundColor:'black',
    }
});
