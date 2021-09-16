import {ListItem} from 'react-native-elements'
import React, {Component} from 'react';
import {View, Text, SafeAreaView, ScrollView, useWindowDimensions} from 'react-native';
import message from 'react-native-message';
import FundArchiveDetail from './FundArchiveDetailView'
import FundArchive from "../components/FundArchive/FundArchive";
import FundStockView from "./FundStockView";
import FundManager from "../components/FundArchive/FundManager";
import ManagerView from "./ManagerView"
import HoldList from "../components/HoldList";
import Header from "../components/Header";
import TabCard from "../components/TabCard";
import {getUserInfo} from "../service/UserService";

const fundSimplify={
    fundName:'aaaaa',
    fundCode:'000001',
    NAV:0.1,
    updateDate:'2021-8-9',
    rate:4,
    oneDayRate:1.5,
    oneYearRate: 1.6
}

const fund = {
    code: '000689',
    size: 14.02,
    start: '2014-08-20',
    managerList: [
        {
            id: '30707945',
            name: '崔宸龙',
            tenure: '1年7个月',
            start: '2020-10-27',
            repay: 113.28,
            uri: 'https://pdf.dfcfw.com/pdf/H8_30707945_1.PNG',
            description: '崔宸龙先生:中国国籍,研究生、博士,曾任深圳市前海安康投资发展有限公司研究部研究员,2017年8月加盟前海开源基金管理有限公司,曾任权益投资本部研究员,现任权益投资本部基金经理。',
        },
        {
            id: '30707945',
            name: '崔宸龙',
            tenure: '1年7个月',
            start: '2020-10-27',
            repay: 113.28,
            uri: 'https://pdf.dfcfw.com/pdf/H8_30707945_1.PNG',
            description: '崔宸龙先生:中国国籍,研究生、博士,曾任深圳市前海安康投资发展有限公司研究部研究员,2017年8月加盟前海开源基金管理有限公司,曾任权益投资本部研究员,现任权益投资本部基金经理。',
        },
    ]
}
const stockList = [
    {
        id: '300014',
        name: '亿纬锂能',
        value: -13.06,
        proportion: 7.78,
    },
    {
        id: '300438',
        name: '鹏辉能源',
        value: -9.19,
        proportion: 7.23,
    },
    {
        id: '600563',
        name: '法拉电子',
        value: +1.52,
        proportion: 6.03,
    },
    {
        id: '300438',
        name: '鹏辉能源',
        value: -9.19,
        proportion: 7.23,
    },
    {
        id: '300014',
        name: '亿纬锂能',
        value: -13.06,
        proportion: 7.78,
    },
    {
        id: '300438',
        name: '鹏辉能源',
        value: -9.19,
        proportion: 7.23,
    },
    {
        id: '300438',
        name: '鹏辉能源',
        value: -9.19,
        proportion: 7.23,
    },
    {
        id: '300014',
        name: '亿纬锂能',
        value: -13.06,
        proportion: 7.78,
    },
    {
        id: '300438',
        name: '鹏辉能源',
        value: -9.19,
        proportion: 7.23,
    },
    {
        id: '300438',
        name: '鹏辉能源',
        value: -9.19,
        proportion: 7.23,
    },
    {
        id: '300014',
        name: '亿纬锂能',
        value: -13.06,
        proportion: 7.78,
    },
    {
        id: '300438',
        name: '鹏辉能源',
        value: -9.19,
        proportion: 7.23,
    },
    {
        id: '300438',
        name: '鹏辉能源',
        value: -9.19,
        proportion: 7.23,
    },
    {
        id: '300014',
        name: '亿纬锂能',
        value: -13.06,
        proportion: 7.78,
    },
    {
        id: '300438',
        name: '鹏辉能源',
        value: -9.19,
        proportion: 7.23,
    },
    {
        id: '300438',
        name: '鹏辉能源',
        value: -9.19,
        proportion: 7.23,
    },
    {
        id: '300014',
        name: '亿纬锂能',
        value: -13.06,
        proportion: 7.78,
    },
    {
        id: '300438',
        name: '鹏辉能源',
        value: -9.19,
        proportion: 7.23,
    },
    {
        id: '300438',
        name: '鹏辉能源',
        value: -9.19,
        proportion: 7.23,
    },
    {
        id: '300014',
        name: '亿纬锂能',
        value: -13.06,
        proportion: 7.78,
    },
]

const archive = {
    name: '前海开源新经济灵活配置混合型证券投资基金',
    code: '000689',
    start: '2014-08-20',
    size: 14.02,
    company: '前海开源基金管理有限公司',
    custodian: '中国工商银行股份有限公司',
    managerList: [
        '崔宸龙先生:中国国籍,研究生、博士,曾任深圳市前海安康投资发展有限公司研究部研究员,2017年8月加盟前海开源基金管理有限公司,曾任权益投资本部研究员,现任权益投资本部基金经理。2020年7月20日起担任前海开源公用事业行业股票型证券投资基金基金经理。2020年7月20日起担任前海开源沪港深非周期性行业股票型证券投资基金基金经理。2020年10月27日起担任前海开源新经济灵活配置混合型证券投资基金基金经理。2021年6月24日起担任前海开源沪港深智慧生活优选灵活配置混合型证券投资基金基金经理。',
    ],
    descriptionList: [
        {
            title: '投资目标',
            content: '本基金主要通过精选投资与新经济相关的优质证券,在合理控制风险并保持基金资产良好流动性的前提下,力争实现基金资产的长期稳定增值。 新经济是指以知识、制度创新为支柱的经济发展方向,不仅仅是经济总量的增长,更包括素质提高、体制改革和福利增长。新经济涵盖新能源、新材料、新海洋、新TMT、新生物、新制造、新技术等各新兴行业的发展。',
        },
        {
            title: '投资目标',
            content: '本基金主要通过精选投资与新经济相关的优质证券,在合理控制风险并保持基金资产良好流动性的前提下,力争实现基金资产的长期稳定增值。 新经济是指以知识、制度创新为支柱的经济发展方向,不仅仅是经济总量的增长,更包括素质提高、体制改革和福利增长。新经济涵盖新能源、新材料、新海洋、新TMT、新生物、新制造、新技术等各新兴行业的发展。',
        },
    ],
}
const manager = {
    name: '崔宸龙',
    tenure: '1年8天',
    description: '崔宸龙先生:中国国籍,研究生、博士,曾任深圳市前海安康投资发展有限公司研究部研究员,2017年8月加盟前海开源基金管理有限公司,曾任权益投资本部研究员,现任权益投资本部基金经理。2020年7月20日起担任前海开源公用事业行业股票型证券投资基金基金经理。2020年7月20日起担任前海开源沪港深非周期性行业股票型证券投资基金基金经理。2020年10月27日起担任前海开源新经济灵活配置混合型证券投资基金基金经理。2021年6月24日起担任前海开源沪港深智慧生活优选灵活配置混合型证券投资基金基金经理。',
    uri: 'https://pdf.dfcfw.com/pdf/H8_30707945_1.PNG',
    fundList: [
        {
            name: '前海开源沪港深智慧生活混合',
            start: '2021-06-24',
            repay: 8.82,
        },
        {
            name: '前海开源新经济混合',
            start: '2020-10-27',
            repay: 111.53,
        },
        {
            name: '前海开源沪港深智慧生活混合',
            start: '2021-06-24',
            repay: 8.82,
        },
        {
            name: '前海开源新经济混合',
            start: '2020-10-27',
            repay: 111.53,
        },
        {
            name: '前海开源沪港深智慧生活混合',
            start: '2021-06-24',
            repay: 8.82,
        },
        {
            name: '前海开源新经济混合',
            start: '2020-10-27',
            repay: 111.53,
        },
        {
            name: '前海开源沪港深智慧生活混合',
            start: '2021-06-24',
            repay: 8.82,
        },
        {
            name: '前海开源新经济混合',
            start: '2020-10-27',
            repay: 111.53,
        },
    ]
}

export default class TestView extends Component {

    constructor(props) {
        super(props);
        console.log("TestView created");
    }

    handleNotLoginNavigate = (direction,naviParam) => {
        getUserInfo((data) => {
            console.log(data)
            if (data.status === 0) {
                console.log("push")
                this.props.navigation.push(direction,naviParam)
            } else {
                this.props.navigation.navigate('Login')
            }
        })
    }

    list = [
        {
            title: '帮助',
            style: {},
            onPress: () => {
            },
        },
        {
            title: '常见问题',
            style: {},
            onPress: () => {
            },
        },
        {
            title: '关于',
            style: {},
            onPress: () => {
                this.props.navigation.navigate('About');
            },
        },
        {
            title:'注册register2',
            style:{},
            onPress:()=>{
                this.props.navigation.navigate('Register2');
            }
        },
        {
            title: '个人信息',
            style: {},
            onPress: () => {
                message.info("请登录")
                this.props.navigation.navigate('Login')
            },
        },
        {
            title: '重仓持股',
            style: {},
            onPress: () => {
                this.props.navigation.navigate('Stock', {stockList: stockList})
            },
            // 测试使用
        },
        {
            title: '基金详情',
            style: {},
            onPress: () => {
                this.props.navigation.navigate('Fund', {code: '000689'})
            },
            // 测试使用
        },
        {
            title: '基金档案',
            style: {},
            onPress: () => {
                this.props.navigation.navigate('FundArchive', {code: '000689'})
            },
            // 测试使用
        },
        {
            title: '基金详情测试000041',
            style: {},
            onPress: () => {
                this.props.navigation.navigate('Fund', {code: '000042'})
            },
            // 测试使用
        },
        {
            title: '基金详情测试货币基金000210',
            style: {},
            onPress: () => {
                this.props.navigation.navigate('Fund', {code: '000210'})
            },
            // 测试使用
        },
        {
            title:'基金分析页测试000689',
            style: {},
            onPress: () => {
                this.props.navigation.navigate('FundAnalysis',{code: '000689'})
            },
            // 测试使用
        },
        {
            title:'风险评测',
            style: {},
            onPress: () => {
                this.props.navigation.navigate('RiskAnalysis',{})
            },
        },
        {
            title:'风险评测结果',
            style: {},
            onPress: () => {
                this.props.navigation.navigate('RiskAnalysisResult',{score:50})
            },
        },
        {
            title: '基金经理',
            style: {},
            onPress: () => {
                this.props.navigation.navigate('Manager', {id: '30707945'})
            },
            // 测试使用
        },
        {
            title: '我的比赛',
            style: {},
            onPress: ()=>this.handleNotLoginNavigate('MyCompeList',null)
            ,
            // 测试使用
        },
        {
            title: '所有比赛',
            style: {},
            onPress: ()=>this.handleNotLoginNavigate('CompeList',null)
            ,
            // 测试使用
        },
        {
            title: '忘记密码测试',
            style: {},
            onPress: () => {
                this.props.navigation.navigate("ForgetPassword",{});
            }
        },
        {
            title: '发送邮箱验证码测试',
            style: {},
            onPress: () => {
                this.props.navigation.navigate("SendVerification",{email:'xtommy@sjtu.edu.cn'});
            }
        },
        {
            title: '重置密码测试',
            style: {},
            onPress: () => {
                this.props.navigation.navigate("ResetPassword",{email:'xtommy@sjtu.edu.cn'});
            }
        },

        // {
        //     title: '加入比赛',
        //     style: {},
        //     onPress: () => {
        //         this.props.navigation.navigate('CompeJoin')
        //     },
        //     // 测试使用
        // },
        // {
        //     title: '基金分析页',
        //     style: {},
        //     onPress: () => {
        //         this.props.navigation.navigate('FundAnalysis',{id:'000689'})
        //     },
        //     // 测试使用
        // },
        // {
        //     title: '历史业绩',
        //     style: {},
        //     onPress: () => {
        //         this.props.navigation.navigate('FundPerformance',{})
        //     },
        //     // 测试使用
        // },
    ]


    render() {

        return (
            <SafeAreaView>
                <Header>我的</Header>
                <ScrollView>
                    <View style={{marginBottom: 100}}>
                        {
                            this.list.map((item, i) => (
                                <ListItem key={i} bottomDivider onPress={item.onPress}>
                                    <ListItem.Content>
                                        <ListItem.Title style={item.style}>{item.title}</ListItem.Title>
                                    </ListItem.Content>
                                    <ListItem.Chevron/>
                                </ListItem>
                            ))
                        }
                    </View>
                </ScrollView>
            </SafeAreaView>
        )
    }
}
