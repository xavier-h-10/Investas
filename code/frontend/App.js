import React from 'react';
import {Component} from "react";
import AppNavigation from "./src/navigation/AppNavigation";
import storage from "./src/components/LocalStorage";

function TestStackScreen() {
    return (
        <HomeStack.Navigator>
            <HomeStack.Screen name="Search" component={SearchView} />
        </HomeStack.Navigator>
    );
}

export default class App extends Component {
    componentDidMount(){
        storage.remove({
            key:'loginState',
        });
    }
    render()  {
        console.disableYellowBox = true; // 关闭全部黄色警告 调试使用
        return <AppNavigation />
    }
}
