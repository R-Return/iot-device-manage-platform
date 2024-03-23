<script>
export default {
  name: "DataQuery",
    data() {
        return {
            // user : JSON.parse(sessionStorage.getItem('CurUser')),
            // tableData: [],
            pageSize:10,
            pageNum:1,
            total:0,
            id:null,
            deviceId:null,
            name:'',
            alert:null,
            alerts:[{
                value: 0,
                label: '正常'
            }, {
                value: 1,
                label: '警报'
            }
            ],
            centerDialogVisible:false
        }
    },
    methods:{
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            this.pageNum=1
            this.pageSize=val
            this.loadPost()
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.pageNum=val
            this.loadPost()
        },
        resetParam(){
            this.deviceId=null,
            this.alert=null
        },
        loadPost(){
            if(this.deviceId!=null){
                this.$axios.post(this.$httpUrl+'/message/listPageC1',{
                    pageSize:this.pageSize,
                    pageNum:this.pageNum,
                    param:{
                        deviceId:this.deviceId,
                        alert:this.alert
                    }
                }).then(res=>res.data).then(res=>{
                    console.log(res)
                    if(res.code==1){
                        this.tableData=res.data
                        this.total=res.total
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
        }
    },
    beforeMount() {
        //this.loadGet();
        // this.loadPost()
    }
}
</script>

<template>
    <div>
        <div style="margin-bottom: 5px;">
            <el-input v-model="deviceId" placeholder="请输入设备id" suffix-icon="el-icon-search" style="width: 200px;"
                      @keyup.enter.native="loadPost"></el-input>
            <el-select v-model="alert" placeholder="请选择消息类型" style="margin-left: 5px;">
                <el-option
                        v-for="item in alerts"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
            <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
            <el-button type="success" @click="resetParam">重置</el-button>

        </div>
        <el-table :data="tableData"
                  :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
                  border
        >
            <el-table-column prop="id" label="ID" width="60">
            </el-table-column>
            <el-table-column prop="deviceid" label="设备id" width="180">
            </el-table-column>
            <el-table-column prop="alert" label="消息类型" width="180">
                <template slot-scope="scope">
                    <el-tag
                            :type="scope.row.alert === 0 ? 'success' : 'danger'"
                            disable-transitions>{{scope.row.alert === 0 ? '正常' :'警报'}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="info" label="消息内容">
            </el-table-column>
            <el-table-column prop="lat" label="纬度">
            </el-table-column>
            <el-table-column prop="lng" label="经度">
            </el-table-column>
            <el-table-column prop="timestamp" label="时间戳">
            </el-table-column>
            <el-table-column prop="value" label="设备发送值">
            </el-table-column>
        </el-table>
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[5, 10, 20,30]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
        </el-pagination>


    </div>
</template>

<style scoped>

</style>