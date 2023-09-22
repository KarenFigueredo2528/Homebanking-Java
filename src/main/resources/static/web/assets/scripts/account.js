const { createApp } = Vue;

const options ={
    data(){
        return{
            account:[],
            transaction: [],
            amount:0,
            description:"",
            dateStartIn:"",
            dateEndIn:"",
            errorMessage:"",
            selectAccount:""
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
                console.log(this.account.number);
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
        },
        downloadpdf(){

            if(this.dateStartIn == "" || this.dateEndIn == ""){
                this.error.Message = "Please enter start date to end date"
                return
            }

            // this.dateStartIn = this.dateStartIn.replace('T' , ' ')
            // this.dateEndIn = this.datesStartIn.replace('T', ' ')
            console.log(this.dateStartIn);
            console.log(this.dateEndIn);
            console.log(this.account.number);
            
            axios.get(`/api/transactions/findDate?dateStart=${this.dateStartIn}:00&dateEnd=${this.dateEndIn}:00&numberAcc=${this.account.number}`,{responseType: 'blob'})
            .then((res) =>{
                console.log(res.data);
                let blob = new Blob([res.data], {type:"application/pdf"})
                let url = window.URL.createObjectURL(blob)


                let a = document.createElement("a")
                a.href = url
                a.download = "transactions_brothers_mindhub.pdf"

                a.click()

                window.URL.revokeObjectURL(url)

                this.dateStartIn =''
                this.dateEndIn =''
                this.errorMessage=''
            }).catch(error =>{
                console.log(error.res.data.text()
                .then(answer =>{
                    console.log(answer);
                }));
            })
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