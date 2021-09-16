import * as React from 'react';
import {Text, useWindowDimensions, View, StyleSheet} from 'react-native';
import {Image} from 'react-native-elements';
import Carousel, {Pagination} from 'react-native-snap-carousel';
// import {Carousel} from '@ant-design/react-native';

import {useEffect, useState} from "react";
import {getBannerAddress} from "../service/BannerService";

export default function HomeCarousel(props) {
  const [activeIndex, setActiveIndex] = useState(0);
  const [carouselImage, setCarouselImage] = useState([]);
  const window = useWindowDimensions();

  const receiveData = (data) => {
    if (data && data.bannerList) {
      let tmp = [];
      let len = data.bannerList.length
      for (let i = 0; i < len; i++) {
        let now = data.bannerList[i];
        if (now.imageUrl) {
          tmp.push(data.bannerList[i]);
        }
      }
      setCarouselImage(tmp);
    }
  }

  useEffect(() => {
        getBannerAddress(receiveData);
      },
      []);


  const _renderItem = ({item, index}) => {
    return (
        <View style={{
          backgroundColor: 'floralwhite',
          borderRadius: 5,
          height: 100,
          padding: 20,
          marginLeft: 16,
          marginRight: 16,
        }}>
          <Text style={{fontSize: 30}}>{item.title}</Text>
          <Text style={{fontSize: 20}}>{item.text}</Text>
        </View>

    )
  }

  const _renderItemImage = ({item, index}) => {
    // console.log(item.imageUrl);
    // console.log(index);
    return (
        <View key={index} style={{height: 70, width: '100%'}}>
          <Image
              source={{uri: item.imageUrl}}
              style={{
                height: '100%',
                width: '90%',
                marginLeft: '5%',
                borderRadius: 10
              }}/>
        </View>
    )
  }

  function pagination() {
    return (
        <Pagination
            dotsLength={carouselImage.length}  //数据源长度
            activeDotIndex={activeIndex}       //当前在哪个点
            dotColor={"#fffefe"}
            inactiveDotColor={"#fffefe"}
            dotStyle={{
              width: 8,
              height: 8,
              borderRadius: 5,
              marginHorizontal: 0.5,
            }}
            inactiveDotOpacity={0.4}
            inactiveDotScale={1}
            containerStyle={{
              position:'absolute',
              top:25,         //TODO:此处可能会出错 需要不同机器上测试 20210829
            }}
        />
    );
  }

  return (
      <View style={{
        flex: 1,
        flexDirection: 'row',
        justifyContent: 'center',
        marginTop: 15,
        height:70,
      }}>
        <Carousel
            layout={"default"}
            data={carouselImage}
            sliderWidth={window.width}
            itemWidth={window.width}
            renderItem={_renderItemImage}
            onSnapToItem={index => setActiveIndex(index)}
            autoplay={true}
            loop={true}
            enableSnap={true}
        />
        { pagination()}




        {/*<Carousel*/}
        {/*  autoplay={true}*/}
        {/*  infinite={true}*/}
        {/*//  swiping={true}*/}
        {/*//  dragging={true}*/}
        {/*  dots={true}*/}
        {/*  selectedIndex={0}*/}
        {/*  >*/}
        {/*  {carouselImage.map((item,index)=>_renderItemImage(item,index))}*/}
        {/*</Carousel>*/}
      </View>
  );
}


const styles = StyleSheet.create({
  wrapper: {
    backgroundColor: '#fff',
  },
  containerHorizontal: {
    flexGrow: 1,
    alignItems: 'center',
    justifyContent: 'center',
    height: 150,
  },
  containerVertical: {
    flexGrow: 1,
    alignItems: 'center',
    justifyContent: 'center',
    height: 150,
  },
  text: {
    color: '#fff',
    fontSize: 36,
  },
});
