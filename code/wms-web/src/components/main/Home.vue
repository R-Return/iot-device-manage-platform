<template>
    <div>
        <el-descriptions  title="物联网设备管理中心" :column="3" size="40" border>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-s-platform"></i>
                    设备总数
                </template>
                {{deviceNum}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-success"></i>
                    当前在线
                </template>
                {{activeNum}}/{{deviceNum}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-info"></i>
                    消息总数
                </template>
                {{dataReceive}}
            </el-descriptions-item>
        </el-descriptions>
        <div class="chart-container">
<!--            <div id="charts" style="width: 400px;height: 300px;"></div>-->
            <div id="charts" style="float:left; width: 100%; height: 400px" ></div>
        </div>
    </div>
</template>

<script>
    // import * as echarts from 'echarts'
    require("echarts/lib/component/legend")

    export default {
        name: "Home",
        data() {
            return {
                user : JSON.parse(sessionStorage.getItem('CurUser')),
                //数据展示
                deviceNum:0,
                activeNum:0,
                dataReceive:0,  //消息总数
                // carDevice:3,
                // homeDevice:2,
                // wearDevice:4,
                // otherDevice:5,
                //饼图
                charts: "",
                deviceId:null,
                types: ["车载设备","智能家居","可穿戴设备","其他设备"],
            };
        },
        methods: {
            //绘制饼图
            drawPie() {
                this.charts = this.$echarts.init(document.getElementById("charts"));
                this.charts.setOption({
                    tooltip: {
                        trigger: "item",
                        formatter: "{a} <br/>{b}:{c} ({d}%)"
                    },
                    legend: {
                        show: true,
                        bottom: 0,
                        left: "center",
                        name: this.types
                    },
                    series: [
                        {
                            name: "统计数量",
                            type: "pie",
                            data:[],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: "rgba(0, 0, 0, 0.5)"
                                },
                                color: function(params) {
                                    //自定义颜色
                                    var colorList = ["#7facdb", "#89e2b2","#eab37b","#d58888"];
                                    return colorList[params.dataIndex];
                                }
                            },
                        }
                    ]
                });
            },
            //动态获取数据
            async initData() {
                this.$axios.post(this.$httpUrl+'/device/countType',{
                    param:{
                        userId:this.user.id
                    }
                }).then(res=>res.data).then(res=>{
                    console.log(res)
                    var typeCount = [];
                    if(res.code==1){
                        console.log("获取成功！")
                        for(let i = 0; i < 4; i++){
                            var obj = new Object();
                            obj.name = this.types[i];
                            obj.value = res.data[i];
                            typeCount[i] = obj;
                        }
                        this.deviceNum = res.data[4];
                        this.activeNum = res.data[5];
                    }else{
                        alert('获取数据失败')
                    }
                    this.charts.setOption({
                        series:[{
                            data: typeCount,
                        }]
                    });
                })
                //获取消息数
                this.$axios.post(this.$httpUrl+'/message/messageNum',{
                    param:{
                        userId:this.user.id
                    }
                }).then(res=>res.data).then(res=> {
                    console.log(res)
                    if (res.code == 1) {
                        console.log("获取成功！")
                        this.dataReceive = res.data;
                    } else {
                        alert('获取数据失败')
                    }
                })
            },
            drawCharts() {
                this.drawPie();
            },
        },
        mounted() {
            // this.$nextTick(function() {
                this.initData();
                this.drawCharts();
            // });
        },
    }
</script>

<style scoped>
.chart-container {
    width: 100%;
    float: left;
}
</style>