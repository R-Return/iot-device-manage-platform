<script>
export default {
  name: "DeviceConfig",
    data() {
        let checkDuplicate =(rule,value,callback)=>{
            if(this.form.id){
                return callback();
            }
            this.$axios.get(this.$httpUrl+"/device/findByName?name="+this.form.name).then(res=>res.data).then(res=>{
                if(res.code!=1){

                    callback()
                }else{
                    callback(new Error('设备名重复！'));
                }
            })
        };

        return {
            user : JSON.parse(sessionStorage.getItem('CurUser')),
            tableData: [],
            pageSize:10,
            pageNum:1,
            total:0,
            id:null,
            name:'',
            type:null,
            types:[{
                    value: 1,
                    label: '车载设备'
                }, {
                    value: 2,
                    label: '智能家居'
                }, {
                    value: 3,
                    label: '可穿戴设备'
                }, {
                    value: 4,
                    label: '其他设备'
                }
            ],
            centerDialogVisible:false,
            form:{
                userid:null,
                id:null,
                name:'',
                description:'',
                type:null
            },
            rules: {
                name: [
                    {required: true, message: '请输入设备名', trigger: 'blur'},
                    {min: 3, max: 40, message: '长度在 3 到 40 个字符', trigger: 'blur'},
                    {validator:checkDuplicate,trigger: 'blur'}
                ]
            }
        }
    },
    methods:{
        resetForm() {
            this.$refs.form.resetFields();
        },
        del(id){
            console.log(id)

            this.$axios.get(this.$httpUrl+'/device/del?id='+id).then(res=>res.data).then(res=>{
                console.log(res)
                if(res.code==1){

                    this.$message({
                        message: '操作成功！',
                        type: 'success'
                    });
                    this.loadPost()
                }else{
                    this.$message({
                        message: '操作失败！',
                        type: 'error'
                    });
                }

            })
        },
        mod(row){
            console.log(row)

            this.centerDialogVisible = true
            this.$nextTick(()=>{
                //赋值到表单
                this.form.id = row.id
                this.form.name = row.name
                this.form.description = row.description
                this.form.type = row.type
            })
        },
        add(){
            console.log("add")
            this.centerDialogVisible = true
            this.$nextTick(()=>{
                this.resetForm()
                this.form.userid = this.user.id

            })

        },
        doSave(){
            console.log(this.form)
            this.$axios.post(this.$httpUrl+'/device/save',this.form).then(res=>res.data).then(res=>{
                console.log(res)
                if(res.code==1){

                    this.$message({
                        message: '操作成功！',
                        type: 'success'
                    });
                    this.centerDialogVisible = false
                    this.loadPost()
                    this.resetForm()
                }else{
                    this.$message({
                        message: '操作失败！',
                        type: 'error'
                    });
                    this.resetForm()
                }

            })
        },
        doMod(){
            console.log("mod")
            this.$axios.post(this.$httpUrl+'/device/update',this.form).then(res=>res.data).then(res=>{
                console.log(res)
                if(res.code==1){

                    this.$message({
                        message: '操作成功！',
                        type: 'success'
                    });
                    this.centerDialogVisible = false
                    this.loadPost()
                    this.resetForm()
                }else{
                    this.$message({
                        message: '操作失败！',
                        type: 'error'
                    });
                    this.resetForm()
                }

            })
        },
        save(){
            this.$refs.form.validate((valid) => {
                if (valid) {
                    if(this.form.id){
                        console.log(this.form.id);
                        this.doMod();
                    }else{
                        this.doSave();
                    }
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });

        },
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
            this.name=''
            this.type=null
        },
        loadPost(){
            this.$axios.post(this.$httpUrl+'/device/listPageC1',{
                pageSize:this.pageSize,
                pageNum:this.pageNum,
                param:{
                    userId:this.user.id,
                    name:this.name,
                    type:this.type
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
        }
    },
    beforeMount() {
        //this.loadGet();
        this.loadPost()
    }
}
</script>

<template>
    <div>
        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="请输入名字" suffix-icon="el-icon-search" style="width: 200px;"
                      @keyup.enter.native="loadPost"></el-input>
            <el-select v-model="type" placeholder="请选择类型" style="margin-left: 5px;">
                <el-option
                        v-for="item in types"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
            <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
            <el-button type="success" @click="resetParam">重置</el-button>

            <el-button type="primary" style="margin-left: 5px;" @click="add">新增</el-button>
        </div>
        <el-table :data="tableData"
                  :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
                  border
        >
            <el-table-column prop="id" label="ID" width="60">
            </el-table-column>
            <el-table-column prop="name" label="设备名" width="180">
            </el-table-column>
            <el-table-column prop="type" label="类型" width="180">
                <template slot-scope="scope">
                    <el-tag
                            :type="scope.row.type === 1 ? 'primary' : (scope.row.type === 2 ? 'success' : (scope.row.type === 3 ? 'warning' : 'danger'))"
                            disable-transitions>{{scope.row.type === 1 ? '车载设备' : (scope.row.type === 2 ? '智能家居' : (scope.row.type === 3 ? '可穿戴设备' : '其他设备'))}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="description" label="描述">
            </el-table-column>
            <el-table-column prop="operate" label="操作">
                <template slot-scope="scope">
                    <el-button size="small" type="success" @click="mod(scope.row)">编辑</el-button>
                    <el-popconfirm
                            title="确定删除吗？"
                            @confirm="del(scope.row.id)"
                            style="margin-left: 5px;"
                    >
                        <el-button slot="reference" size="small" type="danger" >删除</el-button>
                    </el-popconfirm>
                </template>
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

        <el-dialog
                title="提示"
                :visible.sync="centerDialogVisible"
                width="30%"
                center>

            <el-form ref="form" :rules="rules" :model="form" label-width="80px">
                <el-form-item label="设备名" prop="name">
                    <el-col :span="20">
                        <el-input v-model="form.name"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="设备描述" prop="description">
                    <el-col :span="20">
                        <el-input v-model="form.description"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="类型">
                    <el-select v-model="form.type" placeholder="请选择">
                        <el-option
                            v-for="item in types"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>

<style scoped>

</style>