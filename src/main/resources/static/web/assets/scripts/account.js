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
            axios.get(`/api/accounts/${idSearch}`)
            .then(answer =>{
                this.account = answer.data
                console.log(this.account);
                this.transaction = this.account.transactions.sort((a,b)=> b.id - a.id)
                console.log(this.transaction);
            }).catch(error=>console.log("error"));
        },
        deleteAccount(number){
            Swal.fire({
                title: 'Do you want to delete this account?',
                inputAttributes: {
                    autocapitalize: 'off'
                },
                showCancelButton: true,
                confirmButtonText: 'Yes',
                showLoaderOnConfirm: true,
                buttonColor: '#3085d6',
                preConfirm: login => {
                    return axios.patch("/api/clients/current/accounts", `numberAccount=${number}`)
                        .then(answer => {
                            location.reload()
                            location.href = "./accounts.html"
                        }).catch(error => {
                            Swal.fire({
                                icon: 'error',
                                text:error.response.data,
                                confirmButtonColor: '#3085d6'
      
                            });
                        })
                },
                alloweOutside:()=> !Swal.isLoading()
            })
        },
        logOut(){
            axios.post("/api/logout")
            .then(response =>{
                location.href = "../../index.html"
            })
            .catch(error=> console.log(error.message))
        }
    }
}

const app = createApp(options);
app.mount("#app")

/*Dark mode */
const switchButton = document.querySelector("#bg-dark");
const body = document.querySelector("body");
switchButton.addEventListener("click", e => {
  body.classList.toggle("dark-mode"); 
});