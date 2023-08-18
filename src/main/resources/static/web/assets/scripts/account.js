const { createApp } = Vue;

const options ={
    data(){
        return{
            account:[],
            transaction: []
        }
    },
    created(){
        this.loadData()
    },
    methods:{
        loadData(){
            let parametro = location.search
            let params = new URLSearchParams(parametro)
            let idSearch = params.get("id")
            axios.get(`http://localhost:8080/api/accounts/${idSearch}`)
            .then(answer =>{
                this.account = answer.data
                console.log(this.account);
                this.transaction = this.account.transactions.sort((a,b)=> b.id - a.id)
                console.log(this.transaction);
            }).catch(error=>console.log("error"));
        }
    }
}

const app = createApp(options);
app.mount("#app")