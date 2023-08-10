console.log("Hola");
const { createApp } = Vue;

const options = {
    data() {
        return {
            name: "",
            clients: [],
            firstName: "",
            lastName: "",
            email: "",
            json: ""
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData() {
            axios.get("http://localhost:8080/clients")
                .then(answer => {
                    console.log(answer);
                    data = answer.data._embedded;
                    console.log(data);
                    this.clients = data.clients
                    this.json = JSON.stringify(answer.data, null, 1)
                }).catch((error => console.log(error)));
        },
        addClient() {
            let newClient = {
                firstName: this.firstName,
                lastName: this.lastName,
                email: this.email
            }
            axios.post("http://localhost:8080/clients", newClient)
                .then(answer => {
                    this.firstName = "",
                        this.lastName = "",
                        this.email = "",
                        this.loadData()
                }).catch((error => console.log(error)));

        },
        voidInput() {
            if (this.firstName && this.lastName && this.email) {
                this.addClient()
            } else {
                alert("Please fill in all required fields")
            }
        }
    }
}

const app = createApp(options)
app.mount("#app")