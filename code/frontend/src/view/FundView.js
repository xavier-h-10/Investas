import {
    SafeAreaView,
    ScrollView,
    Text,
    useWindowDimensions,
    View
} from "react-native";
import FundArchive from "../components/FundArchive/FundArchive";
import React, {useEffect, useState} from "react";
import Header from "../components/Header";
import {HeaderBackButton} from "@react-navigation/stack";
import FundCard from "../components/FundCard";
import {getFundView} from "../service/FundService";
import Loading from "../components/Loading";
import FundHistory from "../components/FundHistory";
import {Button, Card} from "react-native-elements";
import FundAnalysisCard from "../components/FundAnalysis/FundAnalysisCard";
import WrapCharts from "../components/WrapCharts";
import {getUserInfo} from "../service/UserService";
import {checkPosition, delPosition, putPositionFund} from "../service/UserPositionService";
import Toast from "../components/firebase/Toast";
import {getFundIndicatorNumber} from "../service/FundAnalysisService";
import FundViewPredictionCard from "../components/FundViewPredictionCard";
import ScrollHeader from "../components/ScrollHeader";
import MyStatusBar from "../components/MyStatusBar";

let subHeader1="";

const ScrollViewContent = (props) => {
    // const predictionMock = {
    //     "lastUpdateDate": '2021-09-06',
    //     "hasModel": 1,
    //     "oneDayNAV": 10,
    //     "twoDayNAV": 10,
    //     "threeDayNAV": 10,
    //     "oneDayQuote": 10.00,
    //     "twoDayQuote": 10.00,
    //     "threeDayQuote": -10.00,
    //     "text": "本基金在过去的较长一段时间内（1-3月）维持了较好的上涨势头，根据其过去三天短线下跌的趋势，JAI认为其会在未来三天内进行补涨，趋势如上。",
    // };
    const {
        type,
        fundSimplify,
        fund,
        performance,
        fundRateRank,
        fundRateTotalCount,
        predictionMock,
        code,
        name,
        cancel,
        updateCancel,
        success,
        error
    } = props;

    const [indicatorNumber, setIndicatorNumber] = useState(null);

    function fetchData(data) {
        if (data === null || data.message === undefined || data.message === null) {
            return;
        }
        let tmp = JSON.parse(data.message);
        setIndicatorNumber(tmp);
    }

    useEffect(() => {
        getFundIndicatorNumber(code, fetchData);
    }, [])

    // console.log('fundSimplify', fundSimplify);
    subHeader1 = "";
    if (fundSimplify.NAV == undefined || fundSimplify.NAV == null) {
        subHeader1 += "0.0000";
    } else {
        subHeader1 += fundSimplify.NAV;
    }
    if (fundSimplify.lastOneDayRate == undefined || fundSimplify.lastOneDayRate
        == null) {
        subHeader1 = subHeader1 + " +0.00%";
    } else {
        let tmp = parseFloat(fundSimplify.lastOneDayRate);
        if (tmp >= 0.0) {
            subHeader1 = subHeader1 + "  +" + fundSimplify.lastOneDayRate + "%";
        } else {
            subHeader1 = subHeader1 + "  " + fundSimplify.lastOneDayRate + "%";
        }
    }

    return (
        <View style={{marginBottom: 10}}>
            {
                fundSimplify === undefined ? null :
                    <FundCard
                        fund={fundSimplify}
                        cancel={cancel}
                        updateCancel={updateCancel}
                        success={success}
                        error={error}
                    />
            }
            <FundHistory historyPerformance={performance} fundRateRank={fundRateRank}
                         fundRateTotalCount={fundRateTotalCount} code={code} type={type}/>
            {
                type === 6 ? null :
                    <FundViewPredictionCard
                        fund={fundSimplify}
                        cancel={cancel}
                        updateCancel={updateCancel}
                        success={success}
                        error={error}
                        fundPrediction={predictionMock}
                    />

            }
            <WrapCharts code={code} type={props.type}/>
            {
                indicatorNumber === null ? null :
                    (
                        indicatorNumber.length < 3 ? null : (
                            <Card wrapperStyle={{flexDirection: 'column', padding: 0}}
                                  containerStyle={{borderRadius: 10, borderWidth:0}}>
                                <FundAnalysisCard profit={indicatorNumber[0]} risk={indicatorNumber[1]}
                                                  cost={indicatorNumber[2]} code={code} name={name} title={true}/>
                            </Card>
                        )
                    )
            }
            {
                fund === undefined ? null : <FundArchive fund={fund} type={type}/>
            }
        </View>
    )
}

export default function FundView({route, navigation}) {
    const {code} = route.params;
    const {name} = route.params;
    const [index, setIndex] = useState(2)
    const [error, setError] = useState('')
    const [success, setSuccess] = useState('')
    const [data, setData] = useState(null)
    const [cancel, setCancel] = useState(false);
    const [fundType, setFundType] = useState(0);
    const [header, setHeader] = useState("基金详情");
    const [subHeader, setSubHeader] = useState("");
    const window = useWindowDimensions();

    const updateCancel = (fundCode) => {
        getUserInfo((data) => {
            if (data.status === 0) {
                checkPosition({fundCode: fundCode}, (data) => {
                    if (data.data.exist) {
                        setCancel(true)
                    } else {
                        setCancel(false)
                    }
                })
            } else {
                setCancel(false)
            }
        })
    }
    const handlePosition = () => {
        getUserInfo((data) => {
            if (data.status === 0) {
                if (cancel) {
                    delPosition({"fundCode": code},
                        (data) => {
                            if (data.status === 100) {
                                setSuccess('取消成功')
                            } else {
                                setError('取消失败')
                            }
                            updateCancel(code)
                        })
                } else {
                    putPositionFund({"fund_code": code, "amount": 1},
                        (data) => {
                            if (data.status === 0) {
                                setSuccess('添加成功')
                            } else {
                                setError('添加失败')
                            }
                            updateCancel(code)
                        })
                }
            } else {
                navigation.navigate('Login')
            }
        })
    }
    const handleCompetition = () => {
        getUserInfo((data) => {
            if (data.status === 0) {
                navigation.push('Invest', {code: code, type: fundType})
            } else {
                navigation.navigate('Login')
            }
        })
    }

    console.log("FundView code:", code);
    console.log("FundView name:", name);

    React.useEffect(() => {
        const unsubscribe = navigation.addListener('focus', () => {
            getFundView(code, (data) => {
                console.log("140" + data.data);
                if (data && data.data) {
                    setData(data.data);
                }
                if (data.data && data.data.card && data.data.card.fundType !== undefined && data.data.card.fundType !== null) {
                    setFundType(data.data.card.fundType);
                }
            })
            updateCancel(code)
        });
        // return unsubscribe;
    }, [navigation]);

    return (
        <SafeAreaView>
            <ScrollHeader
                headerLeft={<HeaderBackButton onPress={navigation.goBack} tintColor={"white"}/>}
                title={header}
                subTitle={subHeader}
            >
            </ScrollHeader>
            <ScrollView
                style={{
                    height: fundType === 6 ? window.height *0.94 : window.height * 0.88}}
                onScroll = {(event)=>{{
                    if(event.nativeEvent.contentOffset.y>100) {
                        setHeader(name);
                        setSubHeader(subHeader1);
                    }
                    else {
                        setHeader("基金详情");
                        setSubHeader("");
                    }
                }}}
            >
                <MyStatusBar/>
                <View style={{justifyContent: 'space-between', marginBottom: window.height * 0.02}}>
                    {
                        !data ? <Loading/> :
                            <ScrollViewContent
                                // type={data.card.fundType}
                                //此处进行修改 避免红屏
                                type={fundType}
                                fund={data.archive}
                                fundSimplify={data.card}
                                performance={data.historyPerformance}
                                fundRateRank={data.fundRateRank}
                                fundRateTotalCount={data.fundRateTotalCount}
                                predictionMock={data.predictionMock}
                                code={code}
                                name={name}
                                cancel={cancel}
                                updateCancel={updateCancel}
                                success={(msg) => {
                                    setSuccess(msg)
                                }}
                                error={(msg) => {
                                    setError(msg)
                                }}
                            />
                    }
                </View>
                <Toast message={error} onDismiss={() => setError('')} duration={1000}/>
                <Toast message={success} onDismiss={() => setSuccess('')} duration={1000} type={'success'}/>
            </ScrollView>
            {
                fundType === 6 || !data ? null :
                    <View style={{flexDirection: 'row', position: 'absolute', top: window.height * 0.93}}>
                        <Button
                            disabled={fundType === 6}
                            title={cancel ? '取消自选' : '加入自选'}
                            onPress={(e) => {
                                e.preventDefault()
                                handlePosition()
                            }}
                            buttonStyle={{backgroundColor: 'white', borderRadius: 0, height: window.height * 0.07}}
                            titleStyle={{color: '#2867ff'}}
                            containerStyle={{width: '50%', borderRadius: 0}}
                        />
                        <Button
                            disabled={fundType === 6}
                            title={'加入比赛'}
                            onPress={(e) => {
                                e.preventDefault()
                                handleCompetition()
                            }}
                            buttonStyle={{backgroundColor: '#2867ff', borderRadius: 0, height: window.height * 0.07}}
                            titleStyle={{color: 'white'}}
                            containerStyle={{width: '50%', borderRadius: 0}}
                        />
                    </View>
            }
        </SafeAreaView>
    )
}
