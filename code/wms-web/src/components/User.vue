<script>
export default {
    name: "User",
data(){

    // let checkDuplicate =(rule,value,callback)=>{
    //     this.$axios.get(this.$httpUrl+"/user/findByName?name="+this.registerForm.name).then(res=>res.data).then(res=>{
    //         if(res.code!=1){
    //             callback()
    //         }else{
    //             callback(new Error('账号已经存在'));
    //         }
    //     })
    // };
    let checkEmail =(rule,value,callback)=>{
        var reg = /^([a-zA-Z\d][\w-]{2,})@(\w{2,})\.([a-z]{2,})(\.[a-z]{2,})?$/;
        if(reg.test(value)) {
            callback()
        }else{
            callback(new Error('邮箱格式不正确'));
        }
    };
    return{
        user : JSON.parse(sessionStorage.getItem('CurUser')),
        confirm_disabled:false,
        registerForm:{
            id: this.user.id,
            name: this.user.name,
            password: this.user.password,
            email: this.user.email
        },
        rules:{
            // name: [
            //     { required: true, message: '请输入账号', trigger: 'blur' },
            //     {validator:checkDuplicate, trigger: 'blur'}
            // ],
            password: [
                { required: true, message: '请输密码', trigger: 'blur' },
                {min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}
            ],
            email: [
                { required: true, message: '请输入邮箱', trigger: 'blur' },
                { validator:checkEmail, message: '请输入正确的邮箱地址', trigger: 'blur' }
            ]
        }
    }
},
methods:{

    edit(){
        this.confirm_disabled=true;
        this.$refs.registerForm.validate((valid) => {
            if (valid) { //valid成功为true，失败为false
                //去后台验证用户名密码
                this.$axios.post(this.$httpUrl+'/user/change',this.registerForm).then(res=>res.data).then(res=>{
                    console.log(res)
                    if(res.code==1){
                        //跳转到登录
                        this.$message({message: '修改成功！', type: 'success'});
                        this.confirm_disabled=false;
                    }else{
                        this.confirm_disabled=false;
                        alert('修改失败！请重试');
                        return false;
                    }
                });
            } else {
                this.confirm_disabled=false;
                console.log('修改失败');
                return false;
            }
        });

    },
    cancel(){
        this.$refs.registerForm.resetFields();
    }
}
}
</script>

<template>
    <div>
        <el-form ref="registerForm" :rules="rules" :model="registerForm" label-width="80px">
            <el-form-item label="账号" prop="name">
                <el-col :span="20">
                    <el-label>{{registerForm.name}}</el-label>
                </el-col>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-col :span="20">
                    <el-input v-model="registerForm.password"
                    show-password></el-input>
                </el-col>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
                <el-col :span="20">
                    <el-input v-model="registerForm.email"></el-input>
                </el-col>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="cancel" :disabled="confirm_disabled">取 消</el-button>
                <el-button type="primary" @click="edit" :disabled="confirm_disabled">修 改</el-button>
            </el-form-item>
        </el-form>
</div>
</template>

<style scoped>

</style>