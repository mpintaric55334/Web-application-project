import {Link} from "react-router-dom";

function Header(props) {
    const isLoggedIn = props.isLoggedIn;

    return(            
        <nav className='s-navbar'>
            <div className='s-container-header-component'>
                <Link to='/sinapsa'>
                    <img src={process.env.PUBLIC_URL + '/images/logo-sinappsa2.png'} alt="Sinappsa logo" className="s-logo"/>
                </Link>
            </div>

            <div className='s-container-header-component'>
                <div className='s-dropdown'>
                    <div className='s-dropdown-title'>Menu</div>
                    <ul className='s-dropdown-list'>
                        <li><Link to='/sinapsa' className='s-link'>Početna stranica</Link></li>
                        <li><Link to='/sinapsa/rankings' className='s-link'>Rang Lista</Link></li>
                        <li>{!isLoggedIn && <Link to='/sinapsa/login' className='s-link'>Login</Link>}</li>
                        <li>{isLoggedIn && <Link to='/sinapsa/personalProfile' className='s-link'>Profil</Link>}</li>
                    </ul>
                </div>
            </div>
        </nav>
    )
}

export default Header;

