<script>

import AMapLoader from '@amap/amap-jsapi-loader'
// window._AMapSecurityConfig = {
//     securityJsCode: '0f90f32f577932d220f1b90a76ef7f00'
// }

export default {
    name: "Map",
    data(){
        return{
            tableData:[],
            deviceId:null,
        }
    },
    // mounted() {
    //     this.initAMap();
    // },
    methods:{
        initAMap() {
            AMapLoader.load({
                key: "406ecda731aeb0ac862be1773a3f47d5", // 申请好的Web端开发者Key，首次调用 load 时必填
                version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
                plugins: [], // 需要使用的的插件列表，如比例尺'AMap.Scale'等
            })
                    .then((AMap) => {
                        this.map = new AMap.Map("container", {
                            // 设置地图容器id
                            viewMode: "3D", // 是否为3D地图模式
                            zoom: 11, // 初始化地图级别
                            center: [116.397428, 39.90923], // 初始化地图中心点位置
                        });
                        if(this.tableData.length>0){
                            let markerList = [];
                            let path = [];
                            for(let i=0;i<this.tableData.length;i++){
                                let marker = new AMap.Marker({
                                    position: [this.tableData[i].lng, this.tableData[i].lat],
                                    map: this.map,
                                });
                                markerList.push(marker);
                                path.push([this.tableData[i].lng, this.tableData[i].lat]);
                            }
                            this.map.add(markerList);
                            var polyline = new AMap.Polyline({
                                path: path,
                                borderWeight: 5, // 线条宽度，默认为 1
                                strokeColor: 'red', // 线条颜色
                                lineJoin: 'round' // 折线拐点连接处样式
                            });
                            this.map.add(polyline);
                            this.map.setFitView();
                        }else{
                            this.$message({
                                message: '暂无数据!',
                                type: 'warning'
                            });
                        }

                    })
                    .catch((e) => {
                        console.log(e);
                    });
        },
        loadPost(){
            if(this.deviceId!=null){
                this.$axios.post(this.$httpUrl+'/message/listPageC1',{
                    pageSize:this.pageSize,
                    pageNum:this.pageNum,
                    param:{
                        deviceId:this.deviceId
                    }
                }).then(res=>res.data).then(res=>{
                    console.log(res)
                    if(res.code==1){
                        this.tableData=res.data
                        console.log("准备标记")
                        this.initAMap()
                    }else{
                        alert('获取数据失败')
                    }
                })

            }else{
                this.$message({
                    message: '请输入设备id!',
                    type: 'warning'
                });
            }
        },
        // refresh() {
        //     this.map?.destroy();
        // },
        resetParam(){
            this.deviceId=null
        }
    }

}


</script>

<template>
    <div>
        <div style="margin-bottom: 5px;">
            <el-input v-model="deviceId" placeholder="请输入设备id" suffix-icon="el-icon-search" style="width: 200px;"
                      @keyup.enter.native="loadPost"></el-input>
            <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
            <el-button type="success" @click="resetParam">重置</el-button>
<!--            <el-button type="success" @click="refresh">刷新</el-button>-->
        </div>
        <div id="container" style="width: 100%; height: calc(100vh - 100px)"></div>
    </div>
</template>

<style scoped>
#container{
    padding:0px;
    margin: 0px;
    width: 100%;
    height: 800px;
}
</style>